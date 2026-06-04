package com.example.pago_ms.controller;

import com.example.pago_ms.dto.PagoDTO;
import com.example.pago_ms.service.PagoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pagos")
@Validated
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public PagoDTO crear(@Valid @RequestBody PagoDTO dto) {
        return pagoService.crear(dto);
    }

    @GetMapping
    public List<PagoDTO> listar() {
        return pagoService.listar();
    }

    @GetMapping("/{id}")
    public PagoDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return pagoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public PagoDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                              @Valid @RequestBody PagoDTO dto) {
        return pagoService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        pagoService.eliminar(id);
    }

    @GetMapping("/orden/{ordenId}")
    public List<PagoDTO> listarPorOrden(@PathVariable @Positive(message = "ordenId debe ser positivo") Long ordenId) {
        return pagoService.listarPorOrden(ordenId);
    }

    @PatchMapping("/{id}/aprobar")
    public PagoDTO aprobar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return pagoService.aprobar(id);
    }

    @PatchMapping("/{id}/rechazar")
    public PagoDTO rechazar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return pagoService.rechazar(id);
    }
}