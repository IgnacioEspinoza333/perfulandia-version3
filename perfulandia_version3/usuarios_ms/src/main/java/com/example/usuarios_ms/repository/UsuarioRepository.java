package com.example.usuarios_ms.repository;

import com.example.usuarios_ms.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByEmailIgnoreCase(String email);
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findByNombreContainingIgnoreCase(String q);
}