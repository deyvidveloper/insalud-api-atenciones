package com.insalud.api_gestion_atenciones.repository;

import com.insalud.api_gestion_atenciones.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {

    // Validar si el correo ya existe
    boolean existsByEmail(String email);

    // Obtener persona por email
    Optional<Persona> findByEmail(String email);
}