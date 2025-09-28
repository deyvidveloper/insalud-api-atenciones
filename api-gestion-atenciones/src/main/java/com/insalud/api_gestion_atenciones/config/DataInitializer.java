package com.insalud.api_gestion_atenciones.config;

import com.insalud.api_gestion_atenciones.entity.Persona;
import com.insalud.api_gestion_atenciones.entity.Usuario;
import com.insalud.api_gestion_atenciones.entity.Empleado;
import com.insalud.api_gestion_atenciones.repository.PersonaRepository;
import com.insalud.api_gestion_atenciones.repository.UsuarioRepository;
import com.insalud.api_gestion_atenciones.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PersonaRepository personaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpleadoRepository empleadoRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initAdmin() {
        return args -> {
            if (usuarioRepository.findByUsuario("admin").isEmpty()) {
                Persona persona = new Persona();
                persona.setNombre("Administrador");
                persona.setApellido("General");
                persona.setEmail("admin@insalud.pe");
                persona.setEstado(true);
                personaRepository.save(persona);

                Empleado empleado = new Empleado();
                empleado.setPersona(persona);
                empleado.setRol("ADMIN");
                empleado.setEstado(true);
                empleadoRepository.save(empleado);

                Usuario usuario = new Usuario();
                usuario.setUsuario("admin");
                usuario.setContraseña(passwordEncoder.encode("admin123"));
                usuario.setPersona(persona);
                usuarioRepository.save(usuario);
            }
        };
    }
}