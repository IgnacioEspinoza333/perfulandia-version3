package com.example.proveedores_ms.controller;

import com.example.proveedores_ms.dto.ProveedorDTO;
import com.example.proveedores_ms.service.ProveedorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/proveedores")
@Validated
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @PostMapping
    public ProveedorDTO crear(@Valid @RequestBody ProveedorDTO dto) {
        return proveedorService.crear(dto);
    }

    @GetMapping
    public List<ProveedorDTO> listar() {
        return proveedorService.listar();
    }

    @GetMapping("/{id}")
    public ProveedorDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return proveedorService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public ProveedorDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id, @Valid @RequestBody ProveedorDTO dto) {
        return proveedorService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        proveedorService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<ProveedorDTO> buscar(@RequestParam("q") @NotBlank(message = "q no puede estar vacío") String q) {
        return proveedorService.buscarPorNombre(q);
    }

    @PatchMapping("/{id}/activar")
    public ProveedorDTO activar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return proveedorService.activar(id);
    }

    @PatchMapping("/{id}/desactivar")
    public ProveedorDTO desactivar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return proveedorService.desactivar(id);
    }
}