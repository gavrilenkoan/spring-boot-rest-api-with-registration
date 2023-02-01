package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path  = "api/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @GetMapping("/all")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping
    public ResponseEntity<UserEntity> getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(userService.getUser(jwtService.extractId(token)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PutMapping
    public ResponseEntity<UserEntity> updateUser(HttpServletRequest request, @RequestBody RegistrationDto userDto) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(userService.updateUser(jwtService.extractId(token), userDto));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUser(HttpServletRequest request) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(userService.deleteUser(jwtService.extractId(token)));
    }
}
