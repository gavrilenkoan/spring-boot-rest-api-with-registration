package com.example.demo.service;

import com.example.demo.entity.UserEntity;
import com.example.demo.entity.UserRole;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@AllArgsConstructor
public class SuperAdminService {

    private UserRepository userRepository;

    @Transactional
    public User promoteUser(Long id, Long adminId) {
        if (Objects.equals(id, adminId)) {
            throw new IllegalStateException("you can not change super admin role");
        }

        UserEntity user = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalStateException(("user with id " + id + "not found")));

        user.setRole(UserRole.ROLE_ADMIN);
        return User.toModel(user);
    }

    @Transactional
    public User downgradeUser(Long id, Long adminId) {
        if (Objects.equals(id, adminId)) {
            throw new IllegalStateException("you can not change super admin role");
        }

        UserEntity user = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalStateException(("user with id " + id + "not found")));

        user.setRole(UserRole.ROLE_USER);
        return User.toModel(user);
    }
}
