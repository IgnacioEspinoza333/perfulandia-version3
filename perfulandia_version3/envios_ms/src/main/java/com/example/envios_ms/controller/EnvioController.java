package com.example.envios_ms.controller;

import com.example.envios_ms.dto.EnvioDTO;
import com.example.envios_ms.model.EstadoEnvio;
import com.example.envios_ms.service.EnvioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/envios")
@Validated
public class EnvioController {

    private final EnvioService envioService;

    public EnvioController(EnvioService envioService) {
        this.envioService = envioService;
    }

    // Crear envío
    @PostMapping
    public EnvioDTO crear(@Valid @RequestBody EnvioDTO dto) {
        return envioService.crear(dto);
    }

    // Obtener por ID (útil para backoffice)
    @GetMapping("/{id}")
    public EnvioDTO obtenerPorId(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return envioService.obtenerPorId(id);
    }

    // Seguimiento por número de seguimiento
    @GetMapping("/seguimiento/{numero}")
    public EnvioDTO seguimiento(@PathVariable @NotBlank(message = "numero no puede estar vacío") String numero) {
        return envioService.obtenerPorNumeroSeguimiento(numero);
    }

    // Listar envíos por pedidoId
    @GetMapping("/pedido/{pedidoId}")
    public List<EnvioDTO> listarPorPedido(@PathVariable @Positive(message = "pedidoId debe ser positivo") Long pedidoId) {
        return envioService.listarPorPedido(pedidoId);
    }

    // Actualizar estado del envío (PATCH)
    // Ejemplo: /envios/seguimiento/TRK-AB12CD34/estado?nuevoEstado=EN_TRANSITO&ubicacionActual=Centro%20de%20distribucion
    @PatchMapping("/seguimiento/{numero}/estado")
    public EnvioDTO actualizarEstado(@PathVariable @NotBlank(message = "numero no puede estar vacío") String numero,
                                    @RequestParam EstadoEnvio nuevoEstado,
                                    @RequestParam(required = false) @Size(max = 120, message = "ubicacionActual máximo 120") String ubicacionActual) {
        return envioService.actualizarEstado(numero, nuevoEstado, ubicacionActual);
    }

    // Actualizar ubicación actual (seguimiento)
    @PatchMapping("/seguimiento/{numero}/ubicacion")
    public EnvioDTO actualizarUbicacion(@PathVariable @NotBlank(message = "numero no puede estar vacío") String numero,
                                        @RequestParam @NotBlank(message = "ubicacionActual es obligatoria") @Size(max = 120) String ubicacionActual) {
        return envioService.actualizarUbicacion(numero, ubicacionActual);
    }
}