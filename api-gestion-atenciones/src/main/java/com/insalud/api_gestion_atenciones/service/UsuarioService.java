package com.insalud.api_gestion_atenciones.service;

import com.insalud.api_gestion_atenciones.dto.UsuarioRegistroRequest;
import com.insalud.api_gestion_atenciones.entity.*;
import com.insalud.api_gestion_atenciones.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PersonaRepository personaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PacienteRepository pacienteRepository;
    private final EspecialidadRepository especialidadRepository;
    private final MedicoEspecialidadRepository medicoEspecialidadRepository;
    private final PasswordEncoder passwordEncoder;

    public String registrarUsuario(UsuarioRegistroRequest request) {
        if (personaRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (usuarioRepository.findByUsuario(request.getUsuario()).isPresent()) {
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        // Crear Persona
        Persona persona = new Persona();
        persona.setNombre(request.getNombre());
        persona.setApellido(request.getApellido());
        persona.setEmail(request.getEmail());
        persona.setEstado(true);
        personaRepository.save(persona);

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setUsuario(request.getUsuario());
        usuario.setContraseña(passwordEncoder.encode(request.getContraseña()));
        usuario.setPersona(persona);
        usuarioRepository.save(usuario);

        // Crear rol según tipo
        if ("MEDICO".equalsIgnoreCase(request.getRol())) {
            Empleado empleado = new Empleado();
            empleado.setPersona(persona);
            empleado.setRol("MEDICO");
            empleado.setEstado(true);
            empleadoRepository.save(empleado);

            if (request.getEspecialidadId() != null) {
                Especialidad especialidad = especialidadRepository.findById(request.getEspecialidadId())
                        .orElseThrow(() -> new RuntimeException("Especialidad no encontrada"));

                MedicoEspecialidad me = new MedicoEspecialidad();
                me.setEmpleado(empleado);
                me.setEspecialidad(especialidad);
                medicoEspecialidadRepository.save(me);
            }

        } else if ("PACIENTE".equalsIgnoreCase(request.getRol())) {
            Paciente paciente = new Paciente();
            paciente.setPersona(persona);
            paciente.setRol("PACIENTE");
            paciente.setEstado(true);
            pacienteRepository.save(paciente);
        }

        return "Usuario registrado correctamente";
    }
}