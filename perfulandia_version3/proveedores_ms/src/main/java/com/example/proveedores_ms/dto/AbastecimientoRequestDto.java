package com.example.proveedores_ms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AbastecimientoRequestDto {

    @NotNull(message = "El proveedorId es obligatorio")
    private Long proveedorId;

    @NotNull(message = "El productoId es obligatorio")
    private Long productoId;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "PENDIENTE|COMPLETADO|CANCELADO", message = "El estado debe ser PENDIENTE, COMPLETADO o CANCELADO")
    private String estado;
}
