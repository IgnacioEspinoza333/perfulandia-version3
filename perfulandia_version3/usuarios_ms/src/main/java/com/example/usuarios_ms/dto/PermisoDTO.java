package com.example.usuarios_ms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDTO {

    private Long id;

    @NotBlank(message = "codigo es obligatorio")
    @Size(min = 2, max = 80, message = "codigo debe tener entre 2 y 80 caracteres")
    private String codigo;
}