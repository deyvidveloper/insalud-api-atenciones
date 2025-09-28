package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.entity.Persona;
import com.insalud.api_gestion_atenciones.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personas")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    // Crear persona
    @PostMapping
    public ResponseEntity<Persona> crear(@RequestBody Persona persona) {
        Persona guardada = personaRepository.save(persona);
        return ResponseEntity.ok(guardada);
    }

    // Listar todas las personas
    @GetMapping
    public ResponseEntity<List<Persona>> listar() {
        List<Persona> personas = personaRepository.findAll();
        return ResponseEntity.ok(personas);
    }

    // Obtener una persona por ID
    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable Long id) {
        return personaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}