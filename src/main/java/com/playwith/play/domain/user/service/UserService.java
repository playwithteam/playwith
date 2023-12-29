package com.playwith.play.domain.user.service;


import com.playwith.play.domain.user.controller.NewPasswordForm;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Environment environment;
    @Value("${multipart.profile-images.location}")
    private String profileImageLocation;

    @Transactional
    public SiteUser join(MultipartFile profileImage, String username, String name, String password,
                         String email, String area, String level, LocalDate birthdate) {

        String profileImgUrl = saveProfileImage(profileImage);

        SiteUser siteUser = SiteUser.builder()

                .profileImgUrl(profileImgUrl)
                .username(username)
                .name(name)
                .password(passwordEncoder.encode(password))
                .email(email)
                .area(area)
                .level(level)
                .birthDate(birthdate)
                .build();

        return this.userRepository.save(siteUser);
    }

    private String saveProfileImage(MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            // 프로필 이미지가 없을 경우 null 반환
            return null;
        }

        try {
            String fileName = generateUniqueFileName(profileImage);
            Path uploadPath = Paths.get(profileImageLocation).toAbsolutePath();

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            try (InputStream inputStream = profileImage.getInputStream()) {
                Path filePath = uploadPath.resolve(fileName);
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
                return "/profile-images/" + fileName; // 이 경로를 데이터베이스에 저장
            } catch (IOException e) {
                throw new FileStorageException("Failed to store file " + fileName, e);
            }
        }  catch (IOException e) {
            throw new FileStorageException("Failed to create upload directory", e);
        }
    }

    private String generateUniqueFileName(MultipartFile profileImage) throws IOException {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(profileImage.getOriginalFilename()));
        String baseName = FilenameUtils.getBaseName(originalFileName);
        String extension = FilenameUtils.getExtension(originalFileName);
        String uniqueFileName = baseName + "_" + System.currentTimeMillis() + "." + extension;

        // 파일 이름 중복을 방지하기 위해 유일한 파일 이름 생성
        while (Files.exists(Paths.get(profileImageLocation).resolve(uniqueFileName))) {
            uniqueFileName = baseName + "_" + System.currentTimeMillis() + "." + extension;
        }

        return uniqueFileName;
    }

    // 아이디가 이미 존재하는지 확인하는 로직
    public boolean isUsernameUnique(String username) {
        return !userRepository.existsByUsername(username);
    }

    //소셜 로그인
    @Transactional
    public SiteUser whenSocialLogin(String providerTypeCode, String username, String nickname) {
        Optional<SiteUser> os = this.userRepository.findByUsername(nickname);
        if (os.isPresent()) return os.get();

        return join(null, username, nickname, "", "", "", "", null); // 최초 로그인 시 딱 한번 실행
    }

    //유저아이디 찾기
    public Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    //이메일, 이름 찾기
    public Optional<SiteUser> getUserByEmailAndName(String email, String name) {

        Optional<SiteUser> os = userRepository.findByEmailAndName(email, name);

        // 입력된 이메일,이름이 일치하는 경우에만 사용자 정보를 반환
        return os.filter(u -> u
                .getEmail().equals(email) && u.getName().equals(name));
    }


    //유저 아이디,메일,이름 찾기
    public Optional<SiteUser> getUserUsernameAndMailAndName(String username, String email, String name) {
        Optional<SiteUser> os = this.userRepository.findByUsernameAndEmailAndName(username, email, name);

        // 입력된 아이디,이메일,이름이 일치하는 경우에만 사용자 정보를 반환
        return os.filter(u -> u
                .getUsername().equals(username) && u.getEmail().equals(email) && u.getName().equals(name));
    }

    //비번 수정
    public SiteUser modifyPassword(NewPasswordForm newPasswordForm, SiteUser findUser) {
        String newPassword = newPasswordForm.getPassword1();

        SiteUser siteUserPassword = SiteUser
                .builder()
                .username(findUser.getUsername())
                .name(findUser.getName())
                .id(findUser.getId())
                .password(passwordEncoder.encode(newPassword))
                .birthDate(findUser.getBirthDate())
                .email(findUser.getEmail())
                .nickname(findUser.getNickname())
                .profileImgUrl(findUser.getProfileImgUrl())
                .level(findUser.getLevel())
                .area(findUser.getArea())
                .build();
        return this.userRepository.save(siteUserPassword);
    }
    public String getProfileImageUrlByUsername(String username) {
        Optional<SiteUser> userOptional = userRepository.findByUsername(username);
        return userOptional.map(SiteUser::getProfileImgUrl).orElse("/img/user_img.svg");
    }

    public String getFindProfileImgUrl(SiteUser user) {
        return user.getProfileImgUrl();
    }

    public boolean isPasswordMatching(String enteredPassword, Principal principal) {
        String username = principal.getName();
        Optional<SiteUser> optionalUser = userRepository.findByUsername(username);

        return optionalUser.map(user -> passwordEncoder.matches(enteredPassword, user.getPassword()))
                .orElse(false);
    }
}