package com.example.pedidos_ms.service;

import com.example.pedidos_ms.dto.DetallePedidoDTO;
import com.example.pedidos_ms.dto.RespuestaPedidoDTO;

import java.util.List;

public interface DetallePedidoService {

    RespuestaPedidoDTO agregar(DetallePedidoDTO dto);                  // agrega/suma producto al carrito
    RespuestaPedidoDTO actualizarCantidad(Long detalleId, Integer cantidad);
    void eliminar(Long detalleId);
    List<DetallePedidoDTO> listarPorPedido(Long pedidoId);
}