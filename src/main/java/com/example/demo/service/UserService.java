package com.example.demo.service;

import com.example.demo.dto.AuthenticationRequest;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
    }

    public String signUpUser(UserEntity user) {

        boolean userExists = userRepository.findUserByEmail(user.getEmail()).isPresent();

        if (userExists) {
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        return "works";
    }

    public UserEntity signInUser(AuthenticationRequest request) {
        boolean userExists = userRepository.findUserByEmail(request.getEmail()).isPresent();

        if (!userExists) {
            throw new IllegalStateException("no user with email " + request.getEmail());
        }

        UserEntity user = userRepository.findUserByEmail(request.getEmail()).get();

        if (!bCryptPasswordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("wrong credentials");
        }
        return user;
    }
}
