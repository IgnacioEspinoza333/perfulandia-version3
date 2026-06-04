package com.example.categorias_ms.service;

import java.util.List;

import com.example.categorias_ms.dto.CategoriaRequestDTO;
import com.example.categorias_ms.dto.CategoriaResponseDTO;

public interface CategoriaService {

    CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto);

    List<CategoriaResponseDTO> listarCategorias();

    CategoriaResponseDTO obtenerCategoriaPorId(Long id);

    CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto);

    void eliminarCategoria(Long id);
}