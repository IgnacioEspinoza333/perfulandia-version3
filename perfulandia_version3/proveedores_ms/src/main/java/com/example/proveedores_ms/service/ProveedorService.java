package com.example.proveedores_ms.service;

import com.example.proveedores_ms.dto.AbastecimientoDTO;
import com.example.proveedores_ms.dto.ProveedorDTO;

import java.util.List;

public interface ProveedorService {
    ProveedorDTO crear(ProveedorDTO dto);
    List<ProveedorDTO> listar();
    ProveedorDTO obtenerPorId(Long id);
    ProveedorDTO actualizar(Long id, ProveedorDTO dto);
    void eliminar(Long id);
    List<ProveedorDTO> buscarPorNombre(String q);
    ProveedorDTO activar(Long id);
    ProveedorDTO desactivar(Long id);


    List<AbastecimientoDTO> obtenerAbastecimientos(Long proveedorId);
}
