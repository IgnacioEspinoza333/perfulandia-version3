package com.example.usuarios_ms.repository;

import com.example.usuarios_ms.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Long> {
    boolean existsByNombreIgnoreCase(String nombre);
}