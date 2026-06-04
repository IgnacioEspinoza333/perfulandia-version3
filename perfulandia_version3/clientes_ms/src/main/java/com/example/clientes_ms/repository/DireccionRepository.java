package com.example.clientes_ms.repository;

import com.example.clientes_ms.model.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByCliente_Id(Long clienteId);
    Optional<Direccion> findByCliente_IdAndPrincipalTrue(Long clienteId);
}