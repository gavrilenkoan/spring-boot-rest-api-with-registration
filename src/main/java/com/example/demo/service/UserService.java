package com.example.demo.service;

import com.example.demo.dto.RegistrationDto;
import com.example.demo.entity.UserEntity;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.ReactionRepository;
import com.example.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ReactionRepository reactionRepository;
    private final PostRepository postRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(("user with email " + email + "not found")));
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserEntity getUser(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalStateException(("post with id " + id + "not found")));
    }

    @Transactional
    public UserEntity updateUser(Long id, RegistrationDto userDto) {
        UserEntity user = userRepository.findUserById(id)
                .orElseThrow(() -> new IllegalStateException(("post with id " + id + "not found")));
        if (userDto.getFirstname() != null && !Objects.equals(userDto.getFirstname(), "")) {
            user.setFirstname(userDto.getFirstname());
        }
        if (userDto.getLastname() != null && !Objects.equals(userDto.getLastname(), "")) {
            user.setLastname(userDto.getLastname());
        }
        if (userDto.getPassword() != null && !Objects.equals(userDto.getPassword(), "")) {
            user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getEmail() != null && !Objects.equals(userDto.getEmail(), "") && userRepository.findUserByEmail(userDto.getEmail()).isEmpty()) {
            user.setEmail(userDto.getEmail());
        }
        return user;
    }

    public String deleteUser(Long id) {
        UserEntity user = userRepository.findUserById(id)
                .orElseThrow(() -> new UsernameNotFoundException(("user with id " + id + "not found")));

        reactionRepository.deleteAll(reactionRepository.findAll().stream().filter(r -> r.getUser() == user).toList());
        postRepository.deleteAll(postRepository.findAll().stream().filter(p -> p.getUser() == user).toList());
        userRepository.deleteById(id);
        return "deleted successfully";
    }
}
