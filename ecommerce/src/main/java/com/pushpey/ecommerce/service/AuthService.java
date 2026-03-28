package com.pushpey.ecommerce.service;

import com.pushpey.ecommerce.dto.AuthRequest;
import com.pushpey.ecommerce.dto.AuthResponse;
import com.pushpey.ecommerce.dto.RegisterRequest;
import com.pushpey.ecommerce.entity.Role;
import com.pushpey.ecommerce.entity.User;
import com.pushpey.ecommerce.repository.UserRepository;
import com.pushpey.ecommerce.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // ✅ REGISTER
    public String register(RegisterRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword())) // 🔐 encode
                .role(request.getRole() != null ? request.getRole() : Role.USER) // default role
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    // ✅ LOGIN
    public AuthResponse login(AuthRequest request) {
        System.out.println("LOGIN ATTEMPT: " + request.getEmail());
        System.out.println("RAW PASS: " + request.getPassword());

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        System.out.println("DB PASS: " + user.getPassword());

        System.out.println("MATCH: " + passwordEncoder.matches(request.getPassword(), user.getPassword()));

        // 🔐 authenticate user
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // 🎯 generate JWT
        String token = jwtService.generateToken(request.getEmail());

        return new AuthResponse(token);
    }
}