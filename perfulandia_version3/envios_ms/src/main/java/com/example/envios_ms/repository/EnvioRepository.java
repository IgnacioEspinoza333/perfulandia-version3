package com.example.envios_ms.repository;

import com.example.envios_ms.model.Envio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnvioRepository extends JpaRepository<Envio, Long> {
    Optional<Envio> findByNumeroSeguimiento(String numeroSeguimiento);
    boolean existsByNumeroSeguimiento(String numeroSeguimiento);

    List<Envio> findByPedidoId(Long pedidoId);
}