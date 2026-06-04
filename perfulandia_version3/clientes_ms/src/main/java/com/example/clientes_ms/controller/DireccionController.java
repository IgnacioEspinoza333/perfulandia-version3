package com.example.clientes_ms.controller;

import com.example.clientes_ms.dto.DireccionDTO;
import com.example.clientes_ms.service.DireccionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/direcciones")
@Validated
public class DireccionController {

    private final DireccionService direccionService;

    public DireccionController(DireccionService direccionService) {
        this.direccionService = direccionService;
    }

    // Gestión de direcciones: crear para un cliente
    @PostMapping("/cliente/{clienteId}")
    public DireccionDTO crear(@PathVariable @Positive(message = "clienteId debe ser positivo") Long clienteId,
                              @Valid @RequestBody DireccionDTO dto) {
        // forzamos clienteId desde la URL (seguridad/consistencia)
        dto.setClienteId(clienteId);
        return direccionService.crearParaCliente(clienteId, dto);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<DireccionDTO> listarPorCliente(@PathVariable @Positive(message = "clienteId debe ser positivo") Long clienteId) {
        return direccionService.listarPorCliente(clienteId);
    }

    @GetMapping("/{id}")
    public DireccionDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return direccionService.obtener(id);
    }

    @PutMapping("/{id}")
    public DireccionDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                                   @Valid @RequestBody DireccionDTO dto) {
        return direccionService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        direccionService.eliminar(id);
    }

    // Marcar dirección como principal
    @PatchMapping("/{id}/principal")
    public DireccionDTO marcarPrincipal(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return direccionService.marcarPrincipal(id);
    }
}