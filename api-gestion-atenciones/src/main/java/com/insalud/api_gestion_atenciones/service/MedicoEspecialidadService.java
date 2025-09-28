package com.insalud.api_gestion_atenciones.service;

import com.insalud.api_gestion_atenciones.repository.MedicoEspecialidadRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicoEspecialidadService {

    private final MedicoEspecialidadRepository medicoEspecialidadRepository;

    public MedicoEspecialidadService(MedicoEspecialidadRepository repo) {
        this.medicoEspecialidadRepository = repo;
    }

    public List<String> listarEspecialidadesPorMedico(Long medicoId) {
        return medicoEspecialidadRepository.obtenerEspecialidadesPorMedico(medicoId);
    }
}