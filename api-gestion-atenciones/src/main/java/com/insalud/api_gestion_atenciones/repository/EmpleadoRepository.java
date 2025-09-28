package com.insalud.api_gestion_atenciones.repository;

import com.insalud.api_gestion_atenciones.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByPersonaId(Long personaId);

    List<Empleado> findByRol(String rol);

    @Query("SELECT e FROM Empleado e WHERE e.persona.email = :email AND e.rol = 'MEDICO'")
    Optional<Empleado> findMedicoByEmail(@Param("email") String email);

    void deleteByPersonaId(Long personaId);
}