package com.example.proveedores_ms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimientoDTO {

    private Long id;

    @NotNull(message = "proveedorId es obligatorio")
    @Positive(message = "proveedorId debe ser positivo")
    private Long proveedorId;

    @NotNull(message = "productoId es obligatorio")
    @Positive(message = "productoId debe ser positivo")
    private Long productoId;

    @NotNull(message = "cantidad es obligatoria")
    @Positive(message = "cantidad debe ser mayor a 0")
    private Integer cantidad;

    private String estado; // PENDIENTE, COMPLETADO, CANCELADO
    private Instant fechaCreacion;
}