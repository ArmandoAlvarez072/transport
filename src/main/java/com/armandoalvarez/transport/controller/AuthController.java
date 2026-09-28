package com.armandoalvarez.transport.controller;

import com.armandoalvarez.transport.dto.request.LoginRequest;
import com.armandoalvarez.transport.dto.response.TokenResponse;
import com.armandoalvarez.transport.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;

    @PostMapping("/login")
    public TokenResponse login(@RequestBody LoginRequest request) {

        if (!"admin".equals(request.username()) || !"admin".equals(request.password())) {
            throw new RuntimeException("Invalid credentials");
        }
        return new TokenResponse(jwtService.generateToken(request.username()));
    }

}