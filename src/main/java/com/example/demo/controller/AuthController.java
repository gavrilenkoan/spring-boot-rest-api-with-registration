package com.example.demo.controller;

import com.example.demo.dto.AuthenticationDto;
import com.example.demo.dto.RegistrationDto;
import com.example.demo.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path  = "api/v1/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/registration")
    public ResponseEntity<String> register(@RequestBody RegistrationDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authentication")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationDto request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
