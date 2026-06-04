package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.RolDTO;
import java.util.List;

public interface RolService {

    RolDTO crear(RolDTO dto);
    List<RolDTO> listar();
    RolDTO obtenerPorId(Long id);
    RolDTO actualizar(Long id, RolDTO dto);
    void eliminar(Long id);

    void asignarRolAUsuario(Long usuarioId, Long rolId);

    void asignarPermisoARol(Long rolId, Long permisoId);
}