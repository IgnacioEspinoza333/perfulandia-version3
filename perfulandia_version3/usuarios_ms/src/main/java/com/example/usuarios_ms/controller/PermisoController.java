package com.example.usuarios_ms.controller;

import com.example.usuarios_ms.dto.PermisoDTO;
import com.example.usuarios_ms.service.PermisoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permisos")
@Validated
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @PostMapping
    public PermisoDTO crear(@Valid @RequestBody PermisoDTO dto) {
        return permisoService.crear(dto);
    }

    @GetMapping
    public List<PermisoDTO> listar() {
        return permisoService.listar();
    }

    @GetMapping("/{id}")
    public PermisoDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return permisoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public PermisoDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                                 @Valid @RequestBody PermisoDTO dto) {
        return permisoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        permisoService.eliminar(id);
    }
}