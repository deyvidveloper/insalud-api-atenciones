package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.dto.AuthRequest;
import com.insalud.api_gestion_atenciones.dto.AuthResponse;
import com.insalud.api_gestion_atenciones.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}