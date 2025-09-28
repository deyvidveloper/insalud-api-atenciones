package com.insalud.api_gestion_atenciones.service;

import com.insalud.api_gestion_atenciones.dto.AtencionDTO;
import com.insalud.api_gestion_atenciones.dto.AtencionDetalleDTO;
import com.insalud.api_gestion_atenciones.entity.Atencion;
import com.insalud.api_gestion_atenciones.entity.Empleado;
import com.insalud.api_gestion_atenciones.entity.Paciente;
import com.insalud.api_gestion_atenciones.mapper.AtencionMapper;
import com.insalud.api_gestion_atenciones.repository.AtencionRepository;
import com.insalud.api_gestion_atenciones.repository.EmpleadoRepository;
import com.insalud.api_gestion_atenciones.repository.PacienteRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtencionService {

    private final AtencionRepository atencionRepository;
    private final PacienteRepository pacienteRepository;
    private final EmpleadoRepository empleadoRepository;
    private final AtencionMapper atencionMapper;

    public Page<Atencion> listarTodas(Pageable pageable) {
        return atencionRepository.findAll(pageable);
    }

    public List<Atencion> listarPorPaciente(Long pacienteId) {
        return atencionRepository.findByPacienteId(pacienteId);
    }

    public List<Atencion> listarPorMedico(Long empleadoId) {
        return atencionRepository.findByMedicoId(empleadoId);
    }

    public List<Atencion> listarMias(String correo, Collection<? extends GrantedAuthority> roles) {
        if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_MEDICO"))) {
            Empleado medico = empleadoRepository.findMedicoByEmail(correo)
                    .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));
            return listarPorMedico(medico.getId());
        }

        if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_PACIENTE"))) {
            Paciente paciente = pacienteRepository.findByEmail(correo)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
            return listarPorPaciente(paciente.getId());
        }

        throw new AccessDeniedException("Rol no autorizado");
    }

    //ADMIN también puede ver todas las atenciones detalladas
    public List<AtencionDetalleDTO> listarDetalleMias(String correo, Collection<? extends GrantedAuthority> roles) {
        List<Atencion> atenciones;

        if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_MEDICO"))) {
            Empleado medico = empleadoRepository.findMedicoByEmail(correo)
                    .orElseThrow(() -> new EntityNotFoundException("Médico no encontrado"));
            atenciones = listarPorMedico(medico.getId());
        } else if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_PACIENTE"))) {
            Paciente paciente = pacienteRepository.findByEmail(correo)
                    .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));
            atenciones = listarPorPaciente(paciente.getId());
        } else if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            atenciones = atencionRepository.findAll();
        } else {
            throw new AccessDeniedException("Rol no autorizado");
        }

        return atenciones.stream()
                .map(atencionMapper::mapearAtencion)
                .collect(Collectors.toList());
    }

    public Atencion crear(AtencionDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        Empleado medico = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        Atencion atencion = new Atencion();
        atencion.setFecha(dto.getFecha());
        atencion.setMotivo(dto.getMotivo());
        atencion.setDiagnostico(dto.getDiagnostico());
        atencion.setPaciente(paciente);
        atencion.setMedico(medico);
        atencion.setEstado(dto.getEstado());

        return atencionRepository.save(atencion);
    }

    public Atencion actualizar(Long id, AtencionDTO dto) {
        Atencion atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atención no encontrada"));

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new EntityNotFoundException("Paciente no encontrado"));

        Empleado medico = empleadoRepository.findById(dto.getEmpleadoId())
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado"));

        atencion.setFecha(dto.getFecha());
        atencion.setMotivo(dto.getMotivo());
        atencion.setDiagnostico(dto.getDiagnostico());
        atencion.setPaciente(paciente);
        atencion.setMedico(medico);
        atencion.setEstado(dto.getEstado());

        return atencionRepository.save(atencion);
    }

    public void eliminar(Long id) {
        Atencion atencion = atencionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Atención no encontrada"));

        atencionRepository.delete(atencion);
    }

    public List<Atencion> buscarPorFecha(LocalDate fecha) {
        return atencionRepository.buscarPorFecha(fecha);
    }
}