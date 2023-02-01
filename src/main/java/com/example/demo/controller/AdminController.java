package com.example.demo.controller;

import com.example.demo.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path  = "api/v1/admin")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @DeleteMapping("/user")
    public ResponseEntity<String> deleteUser(@RequestParam Long userId) {
        return ResponseEntity.ok(adminService.deleteUser(userId));
    }

    @DeleteMapping("/post")
    public ResponseEntity<String> deletePost(@RequestParam Long postId) {
        return ResponseEntity.ok(adminService.deletePost(postId));
    }
}
