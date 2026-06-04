package com.example.pedidos_ms.controller;

import com.example.pedidos_ms.dto.PedidoDTO;
import com.example.pedidos_ms.dto.RespuestaPedidoDTO;
import com.example.pedidos_ms.service.PedidoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Validated
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    // Crear pedido => crea carrito
    @PostMapping
    public RespuestaPedidoDTO crear(@Valid @RequestBody PedidoDTO dto) {
        return pedidoService.crearPedido(dto);
    }

    // Obtener pedido + detalles
    @GetMapping("/{pedidoId}")
    public RespuestaPedidoDTO obtener(@PathVariable @Positive(message = "pedidoId debe ser positivo") Long pedidoId) {
        return pedidoService.obtener(pedidoId);
    }

    // Listar todos
    @GetMapping
    public List<PedidoDTO> listar() {
        return pedidoService.listar();
    }

    // Historial por cliente
    @GetMapping("/cliente/{clienteId}")
    public List<PedidoDTO> historial(@PathVariable @Positive(message = "clienteId debe ser positivo") Long clienteId) {
        return pedidoService.historialPorCliente(clienteId);
    }

    // Confirmación de compra
    @PatchMapping("/{pedidoId}/confirmar")
    public RespuestaPedidoDTO confirmar(@PathVariable @Positive(message = "pedidoId debe ser positivo") Long pedidoId) {
        return pedidoService.confirmarCompra(pedidoId);
    }
}
