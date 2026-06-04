package com.example.proveedores_ms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AbastecimientoResponseDto {
    private Long id;
    private Long proveedorId;
    private Long productoId;
    private Integer cantidad;
    private String estado;
    private Instant fechaCreacion;
    private Long version;
}
