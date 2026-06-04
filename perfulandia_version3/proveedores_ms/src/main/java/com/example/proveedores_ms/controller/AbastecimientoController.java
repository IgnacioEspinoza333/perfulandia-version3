package com.example.proveedores_ms.controller;

import com.example.proveedores_ms.dto.AbastecimientoDTO;
import com.example.proveedores_ms.service.AbastecimientoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abastecimientos")
@Validated
public class AbastecimientoController {

    private final AbastecimientoService abastecimientoService;

    public AbastecimientoController(AbastecimientoService abastecimientoService) {
        this.abastecimientoService = abastecimientoService;
    }

    @PostMapping
    public AbastecimientoDTO crear(@Valid @RequestBody AbastecimientoDTO dto) {
        return abastecimientoService.crear(dto);
    }

    @GetMapping
    public List<AbastecimientoDTO> listar() {
        return abastecimientoService.listar();
    }

    @GetMapping("/{id}")
    public AbastecimientoDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return abastecimientoService.obtener(id);
    }

    @GetMapping("/proveedor/{proveedorId}")
    public List<AbastecimientoDTO> listarPorProveedor(@PathVariable @Positive Long proveedorId) {
        return abastecimientoService.listarPorProveedor(proveedorId);
    }

    @GetMapping("/producto/{productoId}")
    public List<AbastecimientoDTO> listarPorProducto(@PathVariable @Positive Long productoId) {
        return abastecimientoService.listarPorProducto(productoId);
    }

    @PatchMapping("/{id}/completar")
    public AbastecimientoDTO completar(@PathVariable @Positive Long id) {
        return abastecimientoService.marcarCompletado(id);
    }

    @PatchMapping("/{id}/cancelar")
    public AbastecimientoDTO cancelar(@PathVariable @Positive Long id) {
        return abastecimientoService.cancelar(id);
    }
}