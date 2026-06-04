package com.example.producto_ms.service;

import com.example.producto_ms.dto.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    CategoriaDTO crear(CategoriaDTO dto);
    List<CategoriaDTO> listar();
    CategoriaDTO obtenerPorId(Long id);
    CategoriaDTO actualizar(Long id, CategoriaDTO dto);
    void eliminar(Long id);

    List<CategoriaDTO> buscarPorNombre(String q);
    CategoriaDTO activar(Long id);
    CategoriaDTO desactivar(Long id);
}
