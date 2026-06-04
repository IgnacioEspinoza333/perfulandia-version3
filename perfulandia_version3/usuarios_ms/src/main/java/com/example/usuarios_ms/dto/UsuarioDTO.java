package com.example.usuarios_ms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "nombre es obligatorio")
    @Size(min = 2, max = 120, message = "nombre debe tener entre 2 y 120 caracteres")
    private String nombre;

    @NotBlank(message = "email es obligatorio")
    @Email(message = "email no es válido")
    @Size(max = 160, message = "email no debe superar 160 caracteres")
    private String email;

    @NotBlank(message = "password es obligatorio")
    @Size(min = 6, max = 60, message = "password debe tener entre 6 y 60 caracteres")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private Boolean activo;
}
