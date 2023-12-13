package com.playwith.play.user;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public SiteUser join(String username, String password, String nickname, String profileImgUrl) {
        SiteUser siteUser = new SiteUser();
        siteUser.setUsername(username);
        siteUser.setNickname(nickname);
        siteUser.setProfileImgUrl(profileImgUrl);
        siteUser.setPassword(passwordEncoder.encode(password));
        return this.userRepository.save(siteUser);
    }

    @Transactional
    public SiteUser whenSocialLogin(String username, String nickname, String profileImgUrl) {
        Optional<SiteUser> opMember = findByUsername(username);

        if (opMember.isPresent()) return opMember.get();

        // 소셜 로그인를 통한 가입시 비번은 없다.
        return join(username, "", nickname, profileImgUrl); // 최초 로그인 시 딱 한번 실행
    }

    public Optional<SiteUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}