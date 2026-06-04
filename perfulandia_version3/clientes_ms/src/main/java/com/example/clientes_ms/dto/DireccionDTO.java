package com.example.clientes_ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DireccionDTO {

    private Long id;

    @NotNull(message = "clienteId es obligatorio")
    @Positive(message = "clienteId debe ser positivo")
    private Long clienteId;

    @NotBlank(message = "calle es obligatoria")
    @Size(min = 2, max = 80, message = "calle debe tener entre 2 y 80 caracteres")
    private String calle;

    @NotBlank(message = "numero es obligatorio")
    @Size(min = 1, max = 10, message = "numero debe tener entre 1 y 10 caracteres")
    private String numero;

    @Size(max = 50, message = "depto máximo 50 caracteres")
    private String depto;

    @NotBlank(message = "comuna es obligatoria")
    @Size(min = 2, max = 60, message = "comuna debe tener entre 2 y 60 caracteres")
    private String comuna;

    @NotBlank(message = "ciudad es obligatoria")
    @Size(min = 2, max = 60, message = "ciudad debe tener entre 2 y 60 caracteres")
    private String ciudad;

    @Size(max = 10, message = "codigoPostal máximo 10 caracteres")
    private String codigoPostal;

    @Size(max = 120, message = "referencia máximo 120 caracteres")
    private String referencia;

    // true si es la dirección principal del cliente
    private Boolean principal;
}