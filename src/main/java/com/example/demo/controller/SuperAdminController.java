package com.example.demo.controller;

import com.example.demo.config.JwtService;
import com.example.demo.model.User;
import com.example.demo.service.SuperAdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(path  = "api/v1/super-admin")
@AllArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;
    private final JwtService jwtService;

    @PutMapping("/to-admin")
    public ResponseEntity<User> promoteUser(HttpServletRequest request, @RequestParam Long userId) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(superAdminService.promoteUser(userId, jwtService.extractId(token)));
    }

    @PutMapping("/to-user")
    public ResponseEntity<User> downgradeUser(HttpServletRequest request, @RequestParam Long userId) {
        String token = request.getHeader(AUTHORIZATION).substring("Bearer ".length());
        return ResponseEntity.ok(superAdminService.downgradeUser(userId, jwtService.extractId(token)));
    }
}
