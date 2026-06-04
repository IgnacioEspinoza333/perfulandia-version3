package com.example.clientes_ms.service;

import com.example.clientes_ms.dto.DireccionDTO;

import java.util.List;

public interface DireccionService {
    DireccionDTO crearParaCliente(Long clienteId, DireccionDTO dto);
    List<DireccionDTO> listarPorCliente(Long clienteId);
    DireccionDTO obtener(Long id);
    DireccionDTO actualizar(Long id, DireccionDTO dto);
    void eliminar(Long id);

    DireccionDTO marcarPrincipal(Long id);
}