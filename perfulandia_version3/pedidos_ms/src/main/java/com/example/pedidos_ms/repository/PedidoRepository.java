package com.example.pedidos_ms.repository;

import com.example.pedidos_ms.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByClienteIdOrderByIdDesc(Long clienteId);
}