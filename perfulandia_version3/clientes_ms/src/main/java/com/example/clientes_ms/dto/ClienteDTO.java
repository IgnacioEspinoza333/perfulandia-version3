package com.example.clientes_ms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClienteDTO {

    private Long id;

    @NotBlank(message = "nombres es obligatorio")
    @Size(min = 2, max = 60, message = "nombres debe tener entre 2 y 60 caracteres")
    private String nombres;

    @NotBlank(message = "apellidos es obligatorio")
    @Size(min = 2, max = 60, message = "apellidos debe tener entre 2 y 60 caracteres")
    private String apellidos;

    @Email(message = "email no tiene formato válido")
    @NotBlank(message = "email es obligatorio")
    @Size(max = 120, message = "email máximo 120 caracteres")
    private String email;

    @Size(min = 6, max = 20, message = "telefono debe tener entre 6 y 20 caracteres")
    private String telefono;

    private Boolean activo;

    // SOLO para "consulta de perfil" (GET /clientes/{id}/perfil)
    private List<DireccionDTO> direcciones;
}