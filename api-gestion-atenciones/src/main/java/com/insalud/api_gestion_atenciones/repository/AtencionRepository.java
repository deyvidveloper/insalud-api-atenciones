package com.insalud.api_gestion_atenciones.repository;

import com.insalud.api_gestion_atenciones.entity.Atencion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AtencionRepository extends JpaRepository<Atencion, Long> {

    List<Atencion> findByPacienteId(Long pacienteId);

    List<Atencion> findByMedicoId(Long medicoId);

    @Query("SELECT a FROM Atencion a WHERE a.fecha = :fecha")
    List<Atencion> buscarPorFecha(LocalDate fecha);

    void deleteByPacienteId(Long pacienteId);
}