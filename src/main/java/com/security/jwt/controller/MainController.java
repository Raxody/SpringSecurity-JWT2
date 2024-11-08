package com.security.jwt.controller;

import com.security.jwt.models.ERole;
import com.security.jwt.models.entity.RoleEntity;
import com.security.jwt.models.entity.UserEntity;
import com.security.jwt.repositories.UserRepository;
import com.security.jwt.request.CreateUserDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class MainController {

    private UserRepository userRepository;

    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello World Not Secured";
    }

    @GetMapping("/helloSecured")
    public String helloSecured() {
        return "Hello World Secured";
    }

    @PostMapping("/hello")
    public ResponseEntity<Object> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) {
        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .role(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity user = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

}
