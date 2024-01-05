package com.playwith.play.domain.user.service;


import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.team.service.TeamService;
import com.playwith.play.domain.user.controller.NewPasswordForm;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import com.playwith.play.global.util.DataNotFoundException;
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
    private final TeamService teamService;

    @Transactional
    public SiteUser join(MultipartFile profileImage, String username, String name, String password,
                         String email, String area, String level, LocalDate birthdate,Team team, int rating) {

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
                .rating(rating)
                .build();
        siteUser.setTeam(team);
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
        } catch (IOException e) {
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

    public SiteUser whenSocialLogin(String providerTypeCode, String name, String username) {
        Optional<SiteUser> os = this.userRepository.findByUsername(username);
        if (os.isPresent()) return os.get();

        return join(null, name, username, "", "", "", "", null, null, 1); // 최초 로그인 시 딱 한번 실행
    }

    //유저아이디 찾기
    public SiteUser findByUsername(String username) {
        Optional<SiteUser> os = this.userRepository.findByUsername(username);
        if (os.isPresent()) {
            return os.get();
        } else {
            throw new DataNotFoundException("siteuser not found");
        }
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
                .rating(findUser.getRating())
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

    public SiteUser modifyUser(MultipartFile profileImage, String username, String name, String password,
                               String email, String area, String level, LocalDate birthdate, Team team, Integer rating) {

        Optional<SiteUser> existingUserOptional = this.userRepository.findByUsername(username);

        if (existingUserOptional.isPresent()) {
            SiteUser existingUser = existingUserOptional.get();

            String profileImgUrl;
            // 프로필 이미지 업데이트
            if(!profileImage.isEmpty()) {
                profileImgUrl = saveProfileImage(profileImage);
            }else {
                profileImgUrl = existingUser.getProfileImgUrl();
            }

            // 기존 사용자 정보를 업데이트
            SiteUser updatedUser = SiteUser.builder()
                    .id(existingUser.getId())
                    .profileImgUrl(profileImgUrl)
                    .username(username)
                    .name(name != null ? name : existingUser.getName())
                    .email(email != null ? email : existingUser.getEmail())
                    .area(area != null ? area : existingUser.getArea())
                    .level(level != null ? level : existingUser.getLevel())
                    .birthDate(birthdate != null ? birthdate : existingUser.getBirthDate())
                    .team(team != null ? team : existingUser.getTeam())
                    .rating(rating != null ? rating : existingUser.getRating())
                    .build();

            // 비밀번호가 입력된 경우에만 업데이트
            if (!StringUtils.isEmpty(password)) {
                updatedUser.setPassword(passwordEncoder.encode(password));
            } else {
                // 사용자가 비밀번호를 변경하지 않은 경우 기존 비밀번호 유지
                updatedUser.setPassword(existingUser.getPassword());
            }

            // 업데이트된 사용자 정보를 저장
            return this.userRepository.save(updatedUser);
        } else {
            return null;
        }
    }
    public int getFindRating(SiteUser user) {
        return user.getRating();
    }

    public SiteUser getUserByName(String name) {
        Optional<SiteUser> siteUser = this.userRepository.findByUsername(name);
        if (siteUser.isPresent()) {
            return siteUser.get();
        }
        else {
            throw new DataNotFoundException("user not found");
        }
    }
}