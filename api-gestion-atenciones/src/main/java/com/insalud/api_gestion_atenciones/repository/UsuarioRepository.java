package com.insalud.api_gestion_atenciones.repository;

import com.insalud.api_gestion_atenciones.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsuario(String usuario);

    Optional<Usuario> findByPersonaEmail(String email);

    boolean existsByUsuario(String usuario);

    void deleteByPersonaId(Long personaId);
}