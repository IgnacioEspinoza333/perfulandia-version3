package com.example.inventario_ms.controller;

import com.example.inventario_ms.dto.InventarioDTO;
import com.example.inventario_ms.dto.StockDTO;
import com.example.inventario_ms.service.InventarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
@Validated
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    // Crear inventario inicial para un producto
    @PostMapping
    public InventarioDTO crear(@Valid @RequestBody InventarioDTO dto) {
        return inventarioService.crear(dto);
    }

    // Listar todo el inventario
    @GetMapping
    public List<InventarioDTO> listar() {
        return inventarioService.listar();
    }

    // Obtener inventario por productoId
    @GetMapping("/producto/{productoId}")
    public InventarioDTO obtenerPorProducto(@PathVariable @Positive(message = "productoId debe ser positivo") Long productoId) {
        return inventarioService.obtenerPorProductoId(productoId);
    }

    // Actualizar stock (setear a un valor exacto)
    @PutMapping("/producto/{productoId}/stock")
    public InventarioDTO actualizarStock(@PathVariable @Positive(message = "productoId debe ser positivo") Long productoId,
                                         @RequestParam @Min(value = 0, message = "nuevoStock no puede ser negativo") Integer nuevoStock) {
        return inventarioService.actualizarStock(productoId, nuevoStock);
    }

    // Aumentar stock
    @PatchMapping("/stock/aumentar")
    public InventarioDTO aumentar(@Valid @RequestBody StockDTO dto) {
        return inventarioService.aumentarStock(dto);
    }

    // Disminuir stock
    @PatchMapping("/stock/disminuir")
    public InventarioDTO disminuir(@Valid @RequestBody StockDTO dto) {
        return inventarioService.disminuirStock(dto);
    }

    // Validación de disponibilidad
    @GetMapping("/disponible")
    public boolean disponible(@RequestParam @Positive(message = "productoId debe ser positivo") Long productoId,
                              @RequestParam @Positive(message = "cantidad debe ser mayor a 0") Integer cantidad) {
        return inventarioService.disponible(productoId, cantidad);
    }
}