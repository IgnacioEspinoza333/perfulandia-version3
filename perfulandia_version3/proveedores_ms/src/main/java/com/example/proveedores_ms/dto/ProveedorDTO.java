package com.example.proveedores_ms.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {

    private Long id;

    @NotBlank(message = "nombre es obligatorio")
    @Size(min = 2, max = 80, message = "nombre debe tener entre 2 y 80 caracteres")
    private String nombre;

    @Email(message = "email no tiene formato válido")
    @Size(max = 120, message = "email máximo 120 caracteres")
    private String email;

    @Size(min = 6, max = 20, message = "telefono debe tener entre 6 y 20 caracteres")
    private String telefono;

    @NotBlank(message = "direccion es obligatoria")
    @Size(min = 5, max = 160, message = "direccion debe tener entre 5 y 160 caracteres")
    private String direccion;

    // Para gestión de proveedores (útil en abastecimiento)
    private Boolean activo;
}