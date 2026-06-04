package com.example.categorias_ms.dto;

public record CategoriaResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        String estado
) {}