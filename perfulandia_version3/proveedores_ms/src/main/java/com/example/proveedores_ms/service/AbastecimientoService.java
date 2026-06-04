package com.example.proveedores_ms.service;

import com.example.proveedores_ms.dto.AbastecimientoDTO;

import java.util.List;

public interface AbastecimientoService {
    AbastecimientoDTO crear(AbastecimientoDTO dto);
    AbastecimientoDTO obtener(Long id);
    List<AbastecimientoDTO> listar();
    List<AbastecimientoDTO> listarPorProveedor(Long proveedorId);
    List<AbastecimientoDTO> listarPorProducto(Long productoId);
    AbastecimientoDTO marcarCompletado(Long id);
    AbastecimientoDTO cancelar(Long id);
}