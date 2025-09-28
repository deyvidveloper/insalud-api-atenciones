package com.insalud.api_gestion_atenciones.security;

import com.insalud.api_gestion_atenciones.entity.Usuario;
import com.insalud.api_gestion_atenciones.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByPersonaEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Correo no encontrado: " + email));

        return new UserDetailsImpl(usuario);
    }
}