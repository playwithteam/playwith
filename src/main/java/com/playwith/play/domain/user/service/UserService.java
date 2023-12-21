package com.playwith.play.domain.user.service;


import com.playwith.play.domain.user.controller.UserCreateForm;
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
        Optional<SiteUser> opMember = findByUsername(username);

        if (opMember.isPresent()) return opMember.get();

        // 소셜 로그인를 통한 가입시 비번은 없다.
        return join("",username,"","","", "", nickname, null); // 최초 로그인 시 딱 한번 실행
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