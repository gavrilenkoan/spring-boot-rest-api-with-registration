package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.dto.RegistrationRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserService userService;

    public String register(RegistrationRequest request) {
        return userService.signUpUser(
                new UserEntity(
                        request.getFirstName(),
                        request.getLastName(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.ROLE_USER
                )
        );
    }

    public UserEntity authenticate(AuthenticationRequest request) {
        return userService.signInUser(request);
    }
}
