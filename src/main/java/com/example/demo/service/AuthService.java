package com.example.demo.service;

import com.example.demo.config.JwtService;
import com.example.demo.dto.AuthenticationDto;
import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRole;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;


    public String register(RegistrationDto request) {
        UserEntity user = new UserEntity(
                        request.getFirstname(),
                        request.getLastname(),
                        request.getEmail(),
                        request.getPassword(),
                        UserRole.ROLE_USER
        );

        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String authenticate(AuthenticationDto request) {
        UserEntity user = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalStateException("no user with email " + request.getEmail()));

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("wrong credentials");
        }
        return jwtService.generateToken(user);
    }
}
