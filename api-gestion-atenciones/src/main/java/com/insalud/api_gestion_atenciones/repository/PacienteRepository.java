package com.insalud.api_gestion_atenciones.repository;

import com.insalud.api_gestion_atenciones.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("SELECT p FROM Paciente p WHERE p.persona.email = :email")
    Optional<Paciente> findByEmail(@Param("email") String email);

    void deleteByPersonaId(Long personaId);
}