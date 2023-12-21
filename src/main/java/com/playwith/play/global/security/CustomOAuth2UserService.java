package com.playwith.play.global.security;

//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class CustomOAuth2UserService extends DefaultOAuth2UserService {
//    private final UserService userService;
//
//    // 카카오톡 로그인이 성공할 때 마다 이 함수가 실행
//    @Override
//    @Transactional
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2User oAuth2User = super.loadUser(userRequest);
//
//        String oauthId = oAuth2User.getName();
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        Map attributesProperties = (Map) attributes.get("properties");
//        String nickname = (String) attributesProperties.get("nickname");
//        String profileImgUrl = (String) attributesProperties.get("profile_image");
//
//        String providerTypeCode = userRequest.getClientRegistration().getRegistrationId().toUpperCase();
//
//        String username = providerTypeCode + "__%s".formatted(oauthId);
//
//        SiteUser siteUser = userService.whenSocialLogin(providerTypeCode, username, nickname, profileImgUrl);
//
//        return new CustomOAuth2User(siteUser.getUsername(), siteUser.getPassword(), siteUser.getGrantedAuthorities());
//    }
//}
//
//class CustomOAuth2User extends User implements OAuth2User {
//
//    public CustomOAuth2User(String username, String password, Collection<? extends GrantedAuthority> authorities) {
//        super(username, password, authorities);
//    }
//
//
//    @Override
//    public Map<String, Object> getAttributes() {
//        return null;
//    }
//
//    @Override
//    public String getName() {
//        return getUsername();
//    }
//}