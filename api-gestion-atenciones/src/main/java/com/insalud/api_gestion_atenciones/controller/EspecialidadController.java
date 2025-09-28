package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.entity.Especialidad;
import com.insalud.api_gestion_atenciones.repository.EspecialidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@RequiredArgsConstructor
public class EspecialidadController {

    private final EspecialidadRepository especialidadRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crear(@RequestBody Especialidad especialidad) {
        especialidad.setEstado(true);
        especialidadRepository.save(especialidad);
        return ResponseEntity.ok("Especialidad registrada correctamente");
    }

    @GetMapping
    public ResponseEntity<List<Especialidad>> listar() {
        return ResponseEntity.ok(especialidadRepository.findAll());
    }
}