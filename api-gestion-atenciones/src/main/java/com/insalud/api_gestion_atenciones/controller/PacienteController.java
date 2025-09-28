package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.entity.Paciente;
import com.insalud.api_gestion_atenciones.entity.Persona;
import com.insalud.api_gestion_atenciones.repository.AtencionRepository;
import com.insalud.api_gestion_atenciones.repository.EmpleadoRepository;
import com.insalud.api_gestion_atenciones.repository.PacienteRepository;
import com.insalud.api_gestion_atenciones.repository.PersonaRepository;
import com.insalud.api_gestion_atenciones.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteController {

    private final PacienteRepository pacienteRepository;
    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final AtencionRepository atencionRepository;

    // Crear paciente vinculado a una persona existente
    @PostMapping
    public ResponseEntity<Paciente> crear(@RequestBody Paciente paciente) {
        Long personaId = paciente.getPersona().getId();
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        paciente.setPersona(persona);
        Paciente guardado = pacienteRepository.save(paciente);
        return ResponseEntity.ok(guardado);
    }

    // Listar todos los pacientes registrados
    @GetMapping
    public ResponseEntity<List<Paciente>> listar() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return ResponseEntity.ok(pacientes);
    }

    // Eliminar paciente por personaId (solo ADMIN)
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    @DeleteMapping("/{personaId}")
    public ResponseEntity<String> eliminar(@PathVariable Long personaId) {
        Persona persona = personaRepository.findById(personaId)
                .orElseThrow(() -> new EntityNotFoundException("Persona no encontrada"));

        atencionRepository.deleteByPacienteId(personaId);
        pacienteRepository.deleteByPersonaId(personaId);
        usuarioRepository.deleteByPersonaId(personaId);
        empleadoRepository.deleteByPersonaId(personaId);
        personaRepository.deleteById(personaId);

        return ResponseEntity.ok("Paciente eliminado correctamente");
    }
}