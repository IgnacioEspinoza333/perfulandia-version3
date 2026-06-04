package com.example.pedidos_ms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {

    private Long id;

    @NotNull(message = "clienteId es obligatorio")
    @Positive(message = "clienteId debe ser positivo")
    private Long clienteId;

    @NotBlank(message = "moneda es obligatoria")
    @Size(min = 3, max = 3, message = "moneda debe tener 3 caracteres (ej: CLP)")
    private String moneda;

    // salida
    private BigDecimal total;

    // salida
    private String estado;
}