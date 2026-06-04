package com.example.pedidos_ms.service;

import com.example.pedidos_ms.dto.DetallePedidoDTO;
import com.example.pedidos_ms.dto.PedidoDTO;
import com.example.pedidos_ms.dto.RespuestaPedidoDTO;
import com.example.pedidos_ms.exception.BusinessException;
import com.example.pedidos_ms.exception.NotFoundException;
import com.example.pedidos_ms.model.DetallePedido;
import com.example.pedidos_ms.model.EstadoPedido;
import com.example.pedidos_ms.model.Pedido;
import com.example.pedidos_ms.repository.DetallePedidoRepository;
import com.example.pedidos_ms.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final DetallePedidoRepository detallePedidoRepository;

    public PedidoServiceImpl(PedidoRepository pedidoRepository,
                             DetallePedidoRepository detallePedidoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    @Override
    @Transactional
    public RespuestaPedidoDTO crearPedido(PedidoDTO dto) {

        Pedido p = new Pedido();
        p.setClienteId(dto.getClienteId());
        p.setMoneda(dto.getMoneda().trim().toUpperCase());
        p.setEstado(EstadoPedido.CARRITO);
        p.setTotal(BigDecimal.ZERO);

        Pedido saved = pedidoRepository.save(p);

        RespuestaPedidoDTO resp = new RespuestaPedidoDTO();
        resp.setPedido(mapPedidoToDTO(saved));
        resp.setDetalles(Collections.emptyList());
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public RespuestaPedidoDTO obtener(Long pedidoId) {
        Pedido p = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: " + pedidoId));

        List<DetallePedidoDTO> detalles = detallePedidoRepository.findByPedido_Id(pedidoId).stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        RespuestaPedidoDTO resp = new RespuestaPedidoDTO();
        resp.setPedido(mapPedidoToDTO(p));
        resp.setDetalles(detalles);
        return resp;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> listar() {
        return pedidoRepository.findAll().stream()
                .map(this::mapPedidoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PedidoDTO> historialPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteIdOrderByIdDesc(clienteId).stream()
                .map(this::mapPedidoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RespuestaPedidoDTO confirmarCompra(Long pedidoId) {

        Pedido p = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: " + pedidoId));

        if (p.getEstado() == EstadoPedido.CONFIRMADO) {
            return obtener(pedidoId);
        }

        List<DetallePedido> detalles = detallePedidoRepository.findByPedido_Id(pedidoId);
        if (detalles.isEmpty()) {
            throw new BusinessException("No puedes confirmar un pedido sin productos");
        }

        BigDecimal total = detalles.stream()
                .map(d -> d.getPrecioUnitario().multiply(BigDecimal.valueOf(d.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        p.setTotal(total);
        p.setEstado(EstadoPedido.CONFIRMADO);
        pedidoRepository.save(p);

        return obtener(pedidoId);
    }

    private PedidoDTO mapPedidoToDTO(Pedido p) {
        PedidoDTO dto = new PedidoDTO();
        dto.setId(p.getId());
        dto.setClienteId(p.getClienteId());
        dto.setMoneda(p.getMoneda());
        dto.setTotal(p.getTotal());
        dto.setEstado(p.getEstado().name());
        return dto;
    }

    private DetallePedidoDTO mapDetalleToDTO(DetallePedido d) {
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setId(d.getId());
        dto.setPedidoId(d.getPedido().getId());
        dto.setProductoId(d.getProductoId());
        dto.setCantidad(d.getCantidad());
        dto.setPrecioUnitario(d.getPrecioUnitario());
        return dto;
    }
}