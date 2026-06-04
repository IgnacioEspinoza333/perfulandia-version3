package com.example.usuarios_ms.repository;

import com.example.usuarios_ms.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByUsuario_Id(Long usuarioId);
}