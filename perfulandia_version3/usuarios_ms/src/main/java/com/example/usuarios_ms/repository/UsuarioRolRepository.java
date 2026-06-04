package com.example.usuarios_ms.repository;

import com.example.usuarios_ms.model.UsuarioRol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRolRepository extends JpaRepository<UsuarioRol, Long> {
    boolean existsByUsuario_IdAndRol_Id(Long usuarioId, Long rolId);
}