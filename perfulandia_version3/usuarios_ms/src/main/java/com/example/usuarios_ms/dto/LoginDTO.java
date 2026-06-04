package com.example.usuarios_ms.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotBlank(message = "email es obligatorio")
    @Email(message = "email no es válido")
    private String email;

    @NotBlank(message = "password es obligatorio")
    private String password;
}
