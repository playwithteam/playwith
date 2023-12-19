package com.playwith.play.domain.user.service;

import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser join(String username, String name, String password,
                         String email, String area, String level, String profileImgUrl, LocalDate birthdate) {
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

    public SiteUser SocialJoin(String username, String password, String nickname, String profileImgUrl) {
        SiteUser siteUser = SiteUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .profileImgUrl(profileImgUrl)
                .build();

        return siteUser;
    }

    @Transactional
    public SiteUser KakaoSocialLogin(String username, String nickname, String profileImgUrl) {
        Optional<SiteUser> opUser = findByUsername(username);
        if (opUser.isPresent()) return opUser.get();

        // 소셜 로그인를 통한 가입시 비번은 없음
        return SocialJoin(username, "", nickname, profileImgUrl); // 최초 로그인 시 딱 한번 실행
    }

    //유저네임 찾기
    public Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<SiteUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public Optional<SiteUser> findByName(String name) {
        return userRepository.findByName(name);
    }

}