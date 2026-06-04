package com.example.pedidos_ms.repository;

import com.example.pedidos_ms.model.DetallePedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido, Long> {

    List<DetallePedido> findByPedido_Id(Long pedidoId);

    Optional<DetallePedido> findByPedido_IdAndProductoId(Long pedidoId, Long productoId);
}