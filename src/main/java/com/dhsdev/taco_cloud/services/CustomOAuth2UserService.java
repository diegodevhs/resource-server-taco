package com.dhsdev.taco_cloud.services;

import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.dhsdev.taco_cloud.security.user.User;
import com.dhsdev.taco_cloud.security.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getName();
        String provider = oAuth2User.getAttribute("sub");

        userRepository.findByEmail(email)
            .orElseGet(() -> {
                User newUser = new User();
                newUser.setUsername(name);
                newUser.setEmail(email);
                newUser.setProvider(provider);
                return userRepository.save(newUser);
            });

            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

            return new DefaultOAuth2User(Collections.singleton(authority), oAuth2User.getAttributes(), "email");

            // return oAuth2User;
    }

}
