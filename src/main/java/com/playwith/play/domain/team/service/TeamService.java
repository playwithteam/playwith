package com.playwith.play.domain.team.service;

import com.playwith.play.domain.team.entity.Team;
import com.playwith.play.domain.team.repository.TeamRepository;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import com.playwith.play.domain.user.service.FileStorageException;
import com.playwith.play.global.util.DataNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    @Value("${multipart.profile-images.location}")
    private String profileImageLocation;

    //팀 생성
    public Team createTeam(MultipartFile profileImage, String teamName, String area, String level, SiteUser user) {
        String profileImgUrl = saveProfileImage(profileImage);
        Team createdTeam = Team
                .builder()
                .profileImgUrl(profileImgUrl)
                .teamName(teamName)
                .area(area)
                .level(level)
                .build();

        createdTeam.addMember(user);

        return teamRepository.save(createdTeam);
    }

    // 아이디로 팀 조회
    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId)
                .orElseThrow(() -> new DataNotFoundException("팀이 존재하지 않습니다. teamId: " + teamId));
    }

    // 팀 리스트 얻기
    public List<Team> getTeamList() {
        return this.teamRepository.findAll();
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

    // 팀 삭제 시 팀 외래 키 null로 설정
    @Transactional
    public void deleteTeamWithNullifyingUsers(Long teamId) {
        Team team = teamRepository.findById(teamId).orElse(null);

        if (team != null) {
            List<SiteUser> usersCopy = new ArrayList<>(team.getSiteUsers());

            for (SiteUser user : usersCopy) {
                user.setTeam(null);
            }

            teamRepository.delete(team);
        }
    }

    //팀 삭제
    public void delete(Team team) {
        this.teamRepository.delete(team);
    }

    //팀 정보 수정
    public Team modifyTeam(Team team, MultipartFile profileImage, String teamName, String area, String level ,SiteUser siteUser) {

        Optional<Team> existingTeamOptional = this.teamRepository.findById(team.getId());

        if (existingTeamOptional.isPresent()) {
            Team existingTeam = existingTeamOptional.get();

            // 프로필 이미지 업데이트
            String profileImgUrl = saveProfileImage(profileImage);

            // 기존 팀 정보를 업데이트
            Team modifyTeam = Team
                    .builder()
                    .id(existingTeam.getId())
                    .profileImgUrl(profileImgUrl)
                    .teamName(teamName)
                    .area(area != null ? area : existingTeam.getArea())
                    .level(level != null ? level : existingTeam.getLevel())
                    .siteUsers(existingTeam.getSiteUsers())
                    .build();
            modifyTeam.getSiteUsers().add(siteUser); // 사용자를 팀에 추가

            return this.teamRepository.save(modifyTeam);
        } else {
            return null;
        }
    }

    public void applyToTeam(Long teamId, SiteUser user) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("팀이 존재하지 않습니다."));

        // 팀에 사용자를 추가하고, 사용자의 팀을 설정
        team.addMember(user);
        // 사용자 정보 저장
        userRepository.save(user);
        // 팀 정보 저장
        teamRepository.save(team);
    }
}
