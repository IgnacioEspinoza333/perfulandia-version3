package com.example.pago_ms.repository;

import com.example.pago_ms.model.EstadoPago;
import com.example.pago_ms.model.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {

    boolean existsByReferenciaIgnoreCase(String referencia);

    List<Pago> findByOrdenId(Long ordenId);

    List<Pago> findByEstado(EstadoPago estado);
}