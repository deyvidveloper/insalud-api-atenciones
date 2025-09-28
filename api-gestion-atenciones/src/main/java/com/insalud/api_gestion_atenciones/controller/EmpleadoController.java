package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.entity.Empleado;
import com.insalud.api_gestion_atenciones.entity.Persona;
import com.insalud.api_gestion_atenciones.repository.EmpleadoRepository;
import com.insalud.api_gestion_atenciones.repository.PersonaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private PersonaRepository personaRepository;

    // Crear empleado (rol MEDICO o ADMIN)
    @PostMapping
    public ResponseEntity<?> crearEmpleado(@RequestBody Empleado empleado) {
        Long personaId = empleado.getPersona().getId();
        Optional<Persona> personaOpt = personaRepository.findById(personaId);
        if (personaOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("La persona con ID " + personaId + " no existe");
        }

        // Verificar si ya existe un empleado con esa persona
        if (empleadoRepository.findByPersonaId(personaId).isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe un empleado vinculado a la persona con ID " + personaId);
        }

        // Vincular persona real
        empleado.setPersona(personaOpt.get());

        // Guardar empleado
        Empleado guardado = empleadoRepository.save(empleado);
        return ResponseEntity.ok(guardado);
    }

    // Listar todos los empleados
    @GetMapping
    public ResponseEntity<List<Empleado>> listarEmpleados() {
        return ResponseEntity.ok(empleadoRepository.findAll());
    }

    // Listar solo médicos
    @GetMapping("/medicos")
    public ResponseEntity<List<Empleado>> listarMedicos() {
        return ResponseEntity.ok(empleadoRepository.findByRol("MEDICO"));
    }

    // Obtener empleado por ID
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtenerPorId(@PathVariable Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));
        return ResponseEntity.ok(empleado);
    }

    // Obtener empleado por personaId
    @GetMapping("/persona/{personaId}")
    public ResponseEntity<Empleado> obtenerPorPersonaId(@PathVariable Long personaId) {
        Empleado empleado = empleadoRepository.findByPersonaId(personaId)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado para persona ID " + personaId));
        return ResponseEntity.ok(empleado);
    }
}