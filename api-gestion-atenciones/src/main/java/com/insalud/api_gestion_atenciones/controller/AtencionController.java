package com.insalud.api_gestion_atenciones.controller;

import com.insalud.api_gestion_atenciones.dto.AtencionDTO;
import com.insalud.api_gestion_atenciones.dto.AtencionDetalleDTO;
import com.insalud.api_gestion_atenciones.entity.Atencion;
import com.insalud.api_gestion_atenciones.mapper.AtencionMapper;
import com.insalud.api_gestion_atenciones.service.AtencionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/atenciones")
@RequiredArgsConstructor
public class AtencionController {

    private final AtencionService atencionService;
    private final AtencionMapper atencionMapper;

    // Listado de todas las atenciones (ADMIN) con paginación
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Atencion>> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(atencionService.listarTodas(pageable));
    }

    // Atenciones del usuario autenticado (MEDICO o PACIENTE o ADMIN) - versión detallada con DTO
    @GetMapping("/mias")
    @PreAuthorize("hasAnyRole('MEDICO', 'PACIENTE', 'ADMIN')")
    public ResponseEntity<List<AtencionDetalleDTO>> listarMias(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                atencionService.listarDetalleMias(userDetails.getUsername(), userDetails.getAuthorities())
        );
    }

    // Atenciones del usuario autenticado (MEDICO o PACIENTE o ADMIN) - alias adicional
    @GetMapping("/mias/detalle")
    @PreAuthorize("hasAnyRole('MEDICO', 'PACIENTE', 'ADMIN')")
    public ResponseEntity<List<AtencionDetalleDTO>> listarMiasDetalle(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(
                atencionService.listarDetalleMias(userDetails.getUsername(), userDetails.getAuthorities())
        );
    }

    // Crear atención (ADMIN/MEDICO)
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<Atencion> crear(@Valid @RequestBody AtencionDTO dto) {
        return ResponseEntity.ok(atencionService.crear(dto));
    }

    // Actualizar atención (ADMIN/MEDICO)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<Atencion> actualizar(@PathVariable Long id, @Valid @RequestBody AtencionDTO dto) {
        return ResponseEntity.ok(atencionService.actualizar(id, dto));
    }

    // Eliminar atención (ADMIN) con mensaje de confirmación
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        atencionService.eliminar(id);
        return ResponseEntity.ok("Atención eliminada correctamente");
    }

    // Consulta personalizada por fecha (ADMIN/MEDICO)
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<List<Atencion>> buscarPorFecha(@RequestParam("fecha") LocalDate fecha) {
        return ResponseEntity.ok(atencionService.buscarPorFecha(fecha));
    }

    // Consulta personalizada por fecha con DTO enriquecido (ADMIN/MEDICO)
    @GetMapping("/buscar/detalle")
    @PreAuthorize("hasAnyRole('ADMIN', 'MEDICO')")
    public ResponseEntity<List<AtencionDetalleDTO>> buscarPorFechaDetalle(@RequestParam("fecha") LocalDate fecha) {
        List<Atencion> atenciones = atencionService.buscarPorFecha(fecha);
        List<AtencionDetalleDTO> dtoList = atenciones.stream()
                .map(atencionMapper::mapearAtencion)
                .toList();
        return ResponseEntity.ok(dtoList);
    }
}