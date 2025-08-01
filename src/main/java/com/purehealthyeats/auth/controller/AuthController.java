package com.purehealthyeats.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.purehealthyeats.auth.dto.SignUpRequest;
import com.purehealthyeats.auth.service.AuthService;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/")
    public String showHomePage() {
        return "Index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "Login";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute SignUpRequest signUpRequest) {
        authService.registerUser(signUpRequest);
        return "redirect:/menu";
    }
}
