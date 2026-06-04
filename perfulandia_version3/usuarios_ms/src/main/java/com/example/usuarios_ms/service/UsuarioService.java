package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.UsuarioDTO;

import java.util.List;

public interface UsuarioService {
    UsuarioDTO crear(UsuarioDTO dto);
    List<UsuarioDTO> listar();
    UsuarioDTO obtenerPorId(Long id);
    UsuarioDTO actualizar(Long id, UsuarioDTO dto);
    void eliminar(Long id);

    List<UsuarioDTO> buscarPorNombre(String q);
    UsuarioDTO activar(Long id);
    UsuarioDTO desactivar(Long id);
}