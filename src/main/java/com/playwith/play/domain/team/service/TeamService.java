package com.playwith.play.domain.team.service;

import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.team.repository.TeamRepository;
import com.playwith.play.domain.user.repository.UserRepository;
import com.playwith.play.domain.user.service.FileStorageException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    @Value("${multipart.profile-images.location}")
    private String profileImageLocation;

    //팀 생성
    public Team createTeam(MultipartFile profileImage, String teamName, String area, String level) {
        // 사용자에게 팀 설정
        String profileImgUrl = saveProfileImage(profileImage);

        Team updatedTeam = Team
                .builder()
                .profileImgUrl(profileImgUrl)
                .teamName(teamName)
                .area(area)
                .level(level)
                .build();

        // 팀 저장 (연관된 사용자 정보도 자동으로 업데이트됨)
       return teamRepository.save(updatedTeam);
    }

    public boolean isTeamNameUnique(String teamname) {
        return !teamRepository.existsByTeamName(teamname);
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
}
