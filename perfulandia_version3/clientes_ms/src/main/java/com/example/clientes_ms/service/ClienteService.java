package com.example.clientes_ms.service;

import com.example.clientes_ms.dto.ClienteDTO;

import java.util.List;

public interface ClienteService {
    ClienteDTO crear(ClienteDTO dto);
    List<ClienteDTO> listar();
    ClienteDTO obtener(Long id);
    ClienteDTO actualizar(Long id, ClienteDTO dto);
    void eliminar(Long id);

    List<ClienteDTO> buscar(String q);

    // Consulta de perfil: cliente + direcciones
    ClienteDTO perfil(Long id);
}