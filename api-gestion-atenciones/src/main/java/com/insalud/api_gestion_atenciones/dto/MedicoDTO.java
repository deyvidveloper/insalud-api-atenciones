package com.insalud.api_gestion_atenciones.dto;

import lombok.Data;
import java.util.List;

@Data
public class MedicoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private List<String> especialidades;
}