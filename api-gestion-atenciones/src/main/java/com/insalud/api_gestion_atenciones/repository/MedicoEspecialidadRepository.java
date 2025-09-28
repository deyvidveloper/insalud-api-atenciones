package com.insalud.api_gestion_atenciones.repository;

import com.insalud.api_gestion_atenciones.entity.MedicoEspecialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicoEspecialidadRepository extends JpaRepository<MedicoEspecialidad, Long> {

    @Query("""
        SELECT me.especialidad.nombre
        FROM MedicoEspecialidad me
        WHERE me.empleado.id = :medicoId
    """)
    List<String> obtenerEspecialidadesPorMedico(@Param("medicoId") Long medicoId);
}