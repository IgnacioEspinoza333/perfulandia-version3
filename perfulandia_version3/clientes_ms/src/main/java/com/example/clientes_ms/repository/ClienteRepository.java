package com.example.clientes_ms.repository;

import com.example.clientes_ms.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    boolean existsByEmailIgnoreCase(String email);
    List<Cliente> findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(String n, String a);
}
