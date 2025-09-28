package com.insalud.api_gestion_atenciones.dto;

import lombok.Data;

@Data
public class PacienteDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}