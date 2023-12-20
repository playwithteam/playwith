package com.playwith.play.domain.user.service;

import com.playwith.play.domain.user.controller.UserCreateForm;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //회원가입
    public SiteUser join(String area, String level, String name, String username, String email, String password, String nickname, String profileImgUrl) {
        SiteUser siteUser = SiteUser
                .builder()
                .username(username)
                .name(name)
                .password(passwordEncoder.encode(password))
                .birthDate(LocalDateTime.now())
                .email(email)
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .level(level)
                .area(area)
                .build();
        return userRepository.save(siteUser);
    }

    //소셜 로그인
    @Transactional
    public SiteUser whenSocialLogin(String providerTypeCode, String username, String nickname, String profileImgUrl) {
        Optional<SiteUser> opUser = findByUsername(username);
        if (opUser.isPresent()) return opUser.get();

        // 소셜 로그인를 통한 가입
        return join(null, null, null, username, null, "", nickname, profileImgUrl); // 최초 로그인 시 딱 한번 실행
    }

    //유저 아이디 찾기
    public Optional<SiteUser> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }


    public Optional<SiteUser> getEmailAndName(String email, String name) {
        return this.userRepository.findByEmailAndName(email, name);
    }

    public Optional<SiteUser> getUserUsernameAndMailAndName(String username, String email, String name) {
        return this.userRepository.findByUsernameAndEmailAndName(username, email, name);
    }

    public SiteUser modifyPassword(UserCreateForm userCreateForm, SiteUser findUser) {
        SiteUser siteUserPassword = SiteUser
                .builder()
                .username(findUser.getUsername())
                .name(findUser.getName())
                .id(findUser.getId())
                .password(passwordEncoder.encode(userCreateForm.getPassword1()))
                .birthDate(findUser.getBirthDate())
                .email(findUser.getEmail())
                .nickname(findUser.getNickname())
                .profileImgUrl(findUser.getProfileImgUrl())
                .level(findUser.getLevel())
                .area(findUser.getArea())
                .build();
        return this.userRepository.save(siteUserPassword);
    }
}