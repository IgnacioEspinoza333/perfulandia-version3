package com.example.usuarios_ms.controller;

import com.example.usuarios_ms.dto.RolDTO;
import com.example.usuarios_ms.service.RolService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Validated
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @PostMapping
    public RolDTO crear(@Valid @RequestBody RolDTO dto) {
        return rolService.crear(dto);
    }

    @GetMapping
    public List<RolDTO> listar() {
        return rolService.listar();
    }

    @GetMapping("/{id}")
    public RolDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return rolService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public RolDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                             @Valid @RequestBody RolDTO dto) {
        return rolService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        rolService.eliminar(id);
    }

    @PostMapping("/{rolId}/usuarios/{usuarioId}")
    public void asignarRolAUsuario(@PathVariable @Positive(message = "rolId debe ser positivo") Long rolId,
                                   @PathVariable @Positive(message = "usuarioId debe ser positivo") Long usuarioId) {
        rolService.asignarRolAUsuario(usuarioId, rolId);
    }

    @PostMapping("/{rolId}/permisos/{permisoId}")
    public void asignarPermisoARol(@PathVariable @Positive(message = "rolId debe ser positivo") Long rolId,
                                   @PathVariable @Positive(message = "permisoId debe ser positivo") Long permisoId) {
        rolService.asignarPermisoARol(rolId, permisoId);
    }
}