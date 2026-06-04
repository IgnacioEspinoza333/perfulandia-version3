package com.example.usuarios_ms.repository;

import com.example.usuarios_ms.model.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermisoRepository extends JpaRepository<Permiso, Long> {
    boolean existsByCodigoIgnoreCase(String codigo);
}