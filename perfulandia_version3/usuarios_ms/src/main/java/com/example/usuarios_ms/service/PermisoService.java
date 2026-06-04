package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.PermisoDTO;

import java.util.List;

public interface PermisoService {
    PermisoDTO crear(PermisoDTO dto);
    List<PermisoDTO> listar();
    PermisoDTO obtenerPorId(Long id);
    PermisoDTO actualizar(Long id, PermisoDTO dto);
    void eliminar(Long id);
}