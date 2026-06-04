package com.example.pago_ms.dto;

import com.example.pago_ms.model.MetodoPago;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoDTO {

    private Long id;

    @NotNull(message = "ordenId es obligatorio")
    @Positive(message = "ordenId debe ser positivo")
    private Long ordenId;

    @NotNull(message = "monto es obligatorio")
    @Positive(message = "monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotBlank(message = "moneda es obligatoria")
    @Size(min = 3, max = 3, message = "moneda debe tener 3 caracteres (ej: CLP)")
    private String moneda;

    @NotNull(message = "metodo es obligatorio")
    private MetodoPago metodo;

    @NotBlank(message = "referencia es obligatoria")
    @Size(min = 6, max = 80, message = "referencia debe tener entre 6 y 80 caracteres")
    private String referencia;

    private String estado; // salida (APROBADO, PENDIENTE, RECHAZADO)
}