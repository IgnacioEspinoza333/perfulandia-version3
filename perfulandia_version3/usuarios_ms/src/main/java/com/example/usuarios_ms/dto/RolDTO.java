package com.example.usuarios_ms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RolDTO {

    private Long id;

    @NotBlank(message = "nombre es obligatorio")
    @Size(min = 2, max = 60, message = "nombre debe tener entre 2 y 60 caracteres")
    private String nombre;

    public RolDTO() {}

    public RolDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}
