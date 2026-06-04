package com.example.inventario_ms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {

    private Long id;

    @NotNull(message = "productoId es obligatorio")
    @Positive(message = "productoId debe ser positivo")
    private Long productoId;

    @NotNull(message = "stock es obligatorio")
    @Min(value = 0, message = "stock no puede ser negativo")
    private Integer stock;
}