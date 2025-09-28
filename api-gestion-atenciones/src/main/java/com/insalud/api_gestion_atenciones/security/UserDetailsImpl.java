package com.insalud.api_gestion_atenciones.security;

import com.insalud.api_gestion_atenciones.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private final Usuario usuario;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Rol dinámico según si es médico o paciente
        String rol = usuario.getPersona().getEmpleado() != null
                ? usuario.getPersona().getEmpleado().getRol()
                : usuario.getPersona().getPaciente() != null
                ? usuario.getPersona().getPaciente().getRol()
                : "PACIENTE";

        return List.of(() -> "ROLE_" + rol.toUpperCase());
    }

    @Override
    public String getPassword() {
        return usuario.getContraseña();
    }

    @Override
    public String getUsername() {
        return usuario.getPersona().getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return usuario.getPersona().getEstado();
    }
}