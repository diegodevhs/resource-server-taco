package com.dhsdev.taco_cloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.dhsdev.taco_cloud.security.user.User;
import com.dhsdev.taco_cloud.security.user.UserRepository;
import com.dhsdev.taco_cloud.services.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

  private final CustomOAuth2UserService oAuth2UserService;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepo) {

    return username -> {
      User user = userRepo.findByUsername(username).get();
      if (user != null)
        return user;
      throw new UsernameNotFoundException("User '" + username + "' not found");
    };
  }

  @Bean
  SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
    return http
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())

        .oauth2Login(oauth2 -> oauth2
            .loginPage("/oauth2/authorization/taco-admin-client")
            .defaultSuccessUrl("/api/ingredients", true)
            .userInfoEndpoint(user -> user.userService(oAuth2UserService)))
        .oauth2Client(Customizer.withDefaults())
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
        .build();
  }

}
