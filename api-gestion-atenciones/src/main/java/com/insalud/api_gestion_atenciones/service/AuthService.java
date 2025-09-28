package com.insalud.api_gestion_atenciones.service;

import com.insalud.api_gestion_atenciones.dto.AuthRequest;
import com.insalud.api_gestion_atenciones.dto.AuthResponse;
import com.insalud.api_gestion_atenciones.repository.UsuarioRepository;
import com.insalud.api_gestion_atenciones.security.JwtService;
import com.insalud.api_gestion_atenciones.security.UserDetailsImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;

    public AuthResponse login(@Valid AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContraseña())
            );

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String correo = userDetails.getUsuario().getPersona().getEmail();
            String token = jwtService.generateToken(userDetails, correo);

            return new AuthResponse(token);

        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }
}