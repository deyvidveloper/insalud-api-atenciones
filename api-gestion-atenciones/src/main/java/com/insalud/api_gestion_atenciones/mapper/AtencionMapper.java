package com.insalud.api_gestion_atenciones.mapper;

import com.insalud.api_gestion_atenciones.dto.AtencionDetalleDTO;
import com.insalud.api_gestion_atenciones.dto.MedicoDTO;
import com.insalud.api_gestion_atenciones.dto.PacienteDTO;
import com.insalud.api_gestion_atenciones.entity.Atencion;
import com.insalud.api_gestion_atenciones.entity.Empleado;
import com.insalud.api_gestion_atenciones.entity.Paciente;
import com.insalud.api_gestion_atenciones.entity.Persona;
import com.insalud.api_gestion_atenciones.service.MedicoEspecialidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AtencionMapper {

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;

    public AtencionDetalleDTO mapearAtencion(Atencion atencion) {
        AtencionDetalleDTO dto = new AtencionDetalleDTO();
        dto.setId(atencion.getId());
        dto.setFecha(atencion.getFecha());
        dto.setMotivo(atencion.getMotivo());
        dto.setDiagnostico(atencion.getDiagnostico());
        dto.setEstado(atencion.getEstado());

        // Medico
        Empleado medico = atencion.getMedico();
        Persona personaMedico = medico.getPersona();

        MedicoDTO medicoDto = new MedicoDTO();
        medicoDto.setId(medico.getId());
        medicoDto.setNombre(personaMedico.getNombre());
        medicoDto.setApellido(personaMedico.getApellido());
        medicoDto.setEmail(personaMedico.getEmail());
        medicoDto.setEspecialidades(
                medicoEspecialidadService.listarEspecialidadesPorMedico(medico.getId())
        );
        dto.setMedico(medicoDto);

        // Paciente
        Paciente paciente = atencion.getPaciente();
        Persona personaPaciente = paciente.getPersona();

        PacienteDTO pacienteDto = new PacienteDTO();
        pacienteDto.setId(paciente.getId());
        pacienteDto.setNombre(personaPaciente.getNombre());
        pacienteDto.setApellido(personaPaciente.getApellido());
        pacienteDto.setEmail(personaPaciente.getEmail());
        dto.setPaciente(pacienteDto);

        return dto;
    }
}