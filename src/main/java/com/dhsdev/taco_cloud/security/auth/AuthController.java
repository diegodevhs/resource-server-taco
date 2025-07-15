package com.dhsdev.taco_cloud.security.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dhsdev.taco_cloud.security.user.LoginRequestDto;
import com.dhsdev.taco_cloud.security.user.RegisterRequestDto;
import com.dhsdev.taco_cloud.security.user.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registerForm(){
        return "registration";
    }

    @GetMapping("/custom-login")
    public String loginForm(){
        return "login";
    }


    @PostMapping("/register")
    public String proccessRegistration(@Valid RegisterRequestDto registerRequestDto, Errors errors ){
        if (errors.hasErrors()) {
            return "registration";
        }
        userRepository.save(registerRequestDto.toUser(passwordEncoder));
        return "redirect:/auth/custom-login";
    }

    @PostMapping("/custom-login")
    public String loggin(@Valid LoginRequestDto loginRequestDto, Errors errors){
        if (errors.hasErrors()) {
            return "login";
        }
        return "redirect:/design"; 

    }

    @ModelAttribute(name = "registerRequestDto")
    public RegisterRequestDto registerRequestDto() {
        return new RegisterRequestDto();
    }

    @ModelAttribute(name = "loginRequestDto")
    public LoginRequestDto loginRequestDto(){
        return new LoginRequestDto();
    }
}
