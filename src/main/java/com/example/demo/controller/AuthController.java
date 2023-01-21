package com.example.demo.controller;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.RegistrationRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path  = "api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @PostMapping("/registration")
    public String register(@RequestBody RegistrationRequest request) {
        return authService.register(request);
    }

    @PostMapping("/authentication")
    public UserEntity authenticate(@RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }
}
