package com.example.producto_ms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    private Long id;

    @NotBlank(message = "nombre es obligatorio")
    @Size(min = 2, max = 120)
    private String nombre;

    @NotBlank(message = "sku es obligatorio")
    @Size(min = 2, max = 40)
    private String sku;

    @NotNull(message = "precio es obligatorio")
    @Positive(message = "precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "stock es obligatorio")
    @PositiveOrZero(message = "stock no puede ser negativo")
    private Integer stock;

    @NotNull(message = "categoriaId es obligatorio")
    @Positive(message = "categoriaId debe ser positivo")
    private Long categoriaId;

    private Boolean activo;
}