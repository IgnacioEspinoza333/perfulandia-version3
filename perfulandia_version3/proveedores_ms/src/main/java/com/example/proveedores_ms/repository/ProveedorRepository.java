package com.example.proveedores_ms.repository;

import com.example.proveedores_ms.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    boolean existsByEmailIgnoreCase(String email);

    List<Proveedor> findByNombreContainingIgnoreCase(String nombre);
}