package com.example.inventario_ms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {

    @NotNull(message = "productoId es obligatorio")
    @Positive(message = "productoId debe ser positivo")
    private Long productoId;

    @NotNull(message = "cantidad es obligatoria")
    @Positive(message = "cantidad debe ser mayor a 0")
    private Integer cantidad;
}