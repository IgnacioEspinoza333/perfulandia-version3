package com.example.usuarios_ms.service;

import com.example.usuarios_ms.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado crearDesdeUsuario(Long usuarioId);
    List<Empleado> listar();
    Empleado obtenerPorId(Long id);
    Empleado activar(Long id);
    Empleado desactivar(Long id);
}