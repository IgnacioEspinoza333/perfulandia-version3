package com.example.pedidos_ms.service;

import com.example.pedidos_ms.dto.PedidoDTO;
import com.example.pedidos_ms.dto.RespuestaPedidoDTO;

import java.util.List;

public interface PedidoService {

    RespuestaPedidoDTO crearPedido(PedidoDTO dto);      // crea carrito
    RespuestaPedidoDTO obtener(Long pedidoId);          // pedido + detalles
    List<PedidoDTO> listar();                           // todos
    List<PedidoDTO> historialPorCliente(Long clienteId);// historial
    RespuestaPedidoDTO confirmarCompra(Long pedidoId);  // confirma
}