package com.playwith.play.domain.user.service;


import com.playwith.play.domain.user.controller.NewPasswordForm;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    //일반 유저
    public SiteUser join(String profileImgUrl, String username, String name, String password,
                         String email, String area, String level, LocalDate birthdate) {
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

    @Transactional
    public SiteUser whenSocialLogin(String providerTypeCode, String username, String nickname) {
        Optional<SiteUser> os = this.userRepository.findByUsername(username);
        if (os.isPresent()) return os.get();

        return join("", username, "", "", "", "", nickname, null); // 최초 로그인 시 딱 한번 실행
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
}