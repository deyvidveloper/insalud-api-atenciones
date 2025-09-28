package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.dto.UsuarioRegistroRequest;
import com.insalud.api_gestion_atenciones.repository.UsuarioRepository;
import com.insalud.api_gestion_atenciones.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @PostMapping("/registro")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrar(@RequestBody UsuarioRegistroRequest request) {
        String mensaje = usuarioService.registrarUsuario(request);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping
    public ResponseEntity<?> listarUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }
}