package com.example.proveedores_ms.repository;

import com.example.proveedores_ms.model.Abastecimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AbastecimientoRepository extends JpaRepository<Abastecimiento, Long> {
    List<Abastecimiento> findByProveedorId(Long proveedorId);
    List<Abastecimiento> findByProductoId(Long productoId);
}