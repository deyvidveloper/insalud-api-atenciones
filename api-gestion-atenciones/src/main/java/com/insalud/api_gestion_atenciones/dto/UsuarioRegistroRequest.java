package com.insalud.api_gestion_atenciones.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRegistroRequest {
    private String nombre;
    private String apellido;
    private String email;
    private String usuario;
    private String contraseña;
    private String rol; // "MEDICO" o "PACIENTE"
    private Long especialidadId; // opcional, solo si es médico
}