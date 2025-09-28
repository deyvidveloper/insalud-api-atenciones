package com.insalud.api_gestion_atenciones.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AtencionDetalleDTO {
    private Long id;
    private LocalDate fecha;
    private String motivo;
    private String diagnostico;
    private Boolean estado;

    private MedicoDTO medico;
    private PacienteDTO paciente;
}