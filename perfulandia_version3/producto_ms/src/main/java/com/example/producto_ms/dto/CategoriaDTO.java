package com.example.producto_ms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {

    private Long id;

    @NotBlank(message = "nombre es obligatorio")
    @Size(min = 2, max = 80, message = "nombre debe tener entre 2 y 80 caracteres")
    private String nombre;

    private Boolean activa;
}
