package net.skhu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import net.skhu.dto.RegistrationRequest;
import net.skhu.entity.User;
import net.skhu.repository.UserRepository;

@RestController
public class RegistrationController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest registrationRequest) {
        User existingUser = userRepository.findByEmail(registrationRequest.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Registration failed: Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(registrationRequest.getPassword());

        User newUser = new User();
        newUser.setEmail(registrationRequest.getEmail());
        newUser.setNickname(registrationRequest.getNickname());
        newUser.setPassword(encodedPassword);
        userRepository.save(newUser);

        return ResponseEntity.ok("Registration successful");
    }
}