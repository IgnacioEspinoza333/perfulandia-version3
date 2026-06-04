package com.example.pedidos_ms.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaPedidoDTO {
    private PedidoDTO pedido;
    private List<DetallePedidoDTO> detalles;
}