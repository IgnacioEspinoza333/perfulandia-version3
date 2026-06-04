package com.example.pedidos_ms.controller;

import com.example.pedidos_ms.dto.DetallePedidoDTO;
import com.example.pedidos_ms.dto.RespuestaPedidoDTO;
import com.example.pedidos_ms.service.DetallePedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalles")
@Validated
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    // Agregar producto al carrito
    @PostMapping
    public RespuestaPedidoDTO agregar(@Valid @RequestBody DetallePedidoDTO dto) {
        return detallePedidoService.agregar(dto);
    }

    // Actualizar cantidad
    @PatchMapping("/{detalleId}/cantidad/{cantidad}")
    public RespuestaPedidoDTO actualizarCantidad(
            @PathVariable @Positive(message = "detalleId debe ser positivo") Long detalleId,
            @PathVariable @Positive(message = "cantidad debe ser mayor a 0") Integer cantidad
    ) {
        return detallePedidoService.actualizarCantidad(detalleId, cantidad);
    }

    // Eliminar detalle
    @DeleteMapping("/{detalleId}")
    public void eliminar(@PathVariable @Positive(message = "detalleId debe ser positivo") Long detalleId) {
        detallePedidoService.eliminar(detalleId);
    }

    // Listar detalles por pedido
    @GetMapping("/pedido/{pedidoId}")
    public List<DetallePedidoDTO> listarPorPedido(
            @PathVariable @Positive(message = "pedidoId debe ser positivo") Long pedidoId
    ) {
        return detallePedidoService.listarPorPedido(pedidoId);
    }
}