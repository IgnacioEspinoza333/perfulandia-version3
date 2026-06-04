package com.example.pedidos_ms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {

    private Long id;

    @NotNull(message = "pedidoId es obligatorio")
    @Positive(message = "pedidoId debe ser positivo")
    private Long pedidoId;

    @NotNull(message = "productoId es obligatorio")
    @Positive(message = "productoId debe ser positivo")
    private Long productoId;

    @NotNull(message = "cantidad es obligatoria")
    @Positive(message = "cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull(message = "precioUnitario es obligatorio")
    @Positive(message = "precioUnitario debe ser mayor a 0")
    private BigDecimal precioUnitario;
}