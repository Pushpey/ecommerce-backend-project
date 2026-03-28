package com.pushpey.ecommerce.controller;

import com.pushpey.ecommerce.dto.AuthRequest;
import com.pushpey.ecommerce.dto.AuthResponse;
import com.pushpey.ecommerce.dto.RegisterRequest;
import com.pushpey.ecommerce.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //  Register API
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    //  Login API
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}