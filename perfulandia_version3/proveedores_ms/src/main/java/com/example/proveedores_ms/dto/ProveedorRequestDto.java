package com.example.proveedores_ms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProveedorRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 80, message = "El nombre debe tener entre 2 y 80 caracteres")
    private String nombre;

    @Email(message = "El email no es válido")
    @Size(max = 120, message = "El email no puede superar los 120 caracteres")
    private String email;

    @Size(min = 6, max = 20, message = "El teléfono debe tener entre 6 y 20 caracteres")
    private String telefono;

    @NotBlank(message = "La dirección es obligatoria")
    @Size(min = 5, max = 160, message = "La dirección debe tener entre 5 y 160 caracteres")
    private String direccion;

    private Boolean activo = true;
}
