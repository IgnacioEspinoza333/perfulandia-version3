package com.example.producto_ms.repository;

import com.example.producto_ms.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    boolean existsBySkuIgnoreCase(String sku);

    List<Producto> findByNombreContainingIgnoreCase(String nombre);

    List<Producto> findByCategoria_Id(Long categoriaId);
}