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
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;
    private final PedidoRepository pedidoRepository;

    public DetallePedidoServiceImpl(DetallePedidoRepository detallePedidoRepository,
                                    PedidoRepository pedidoRepository) {
        this.detallePedidoRepository = detallePedidoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    @Transactional
    public RespuestaPedidoDTO agregar(DetallePedidoDTO dto) {

        Pedido pedido = pedidoRepository.findById(dto.getPedidoId())
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: " + dto.getPedidoId()));

        if (pedido.getEstado() != EstadoPedido.CARRITO) {
            throw new BusinessException("Solo puedes modificar el carrito si el pedido está en estado CARRITO");
        }

        DetallePedido detalle = detallePedidoRepository
                .findByPedido_IdAndProductoId(dto.getPedidoId(), dto.getProductoId())
                .orElse(null);

        if (detalle == null) {
            detalle = new DetallePedido();
            detalle.setPedido(pedido);
            detalle.setProductoId(dto.getProductoId());
            detalle.setCantidad(dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
        } else {
            detalle.setCantidad(detalle.getCantidad() + dto.getCantidad());
            detalle.setPrecioUnitario(dto.getPrecioUnitario());
        }

        detallePedidoRepository.save(detalle);
        recalcularTotal(pedido.getId());

        return construirRespuesta(pedido.getId());
    }

    @Override
    @Transactional
    public RespuestaPedidoDTO actualizarCantidad(Long detalleId, Integer cantidad) {

        DetallePedido d = detallePedidoRepository.findById(detalleId)
                .orElseThrow(() -> new NotFoundException("DetallePedido no encontrado: " + detalleId));

        Pedido pedido = d.getPedido();
        if (pedido.getEstado() != EstadoPedido.CARRITO) {
            throw new BusinessException("Solo puedes modificar el carrito si el pedido está en estado CARRITO");
        }

        d.setCantidad(cantidad);
        detallePedidoRepository.save(d);
        recalcularTotal(pedido.getId());

        return construirRespuesta(pedido.getId());
    }

    @Override
    @Transactional
    public void eliminar(Long detalleId) {

        DetallePedido d = detallePedidoRepository.findById(detalleId)
                .orElseThrow(() -> new NotFoundException("DetallePedido no encontrado: " + detalleId));

        Pedido pedido = d.getPedido();
        if (pedido.getEstado() != EstadoPedido.CARRITO) {
            throw new BusinessException("Solo puedes modificar el carrito si el pedido está en estado CARRITO");
        }

        detallePedidoRepository.deleteById(detalleId);
        recalcularTotal(pedido.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DetallePedidoDTO> listarPorPedido(Long pedidoId) {
        return detallePedidoRepository.findByPedido_Id(pedidoId).stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());
    }

    // ---------- helpers ----------

    private void recalcularTotal(Long pedidoId) {
        Pedido p = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: " + pedidoId));

        List<DetallePedido> detalles = detallePedidoRepository.findByPedido_Id(pedidoId);

        BigDecimal total = detalles.stream()
                .map(x -> x.getPrecioUnitario().multiply(BigDecimal.valueOf(x.getCantidad())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        p.setTotal(total);
        pedidoRepository.save(p);
    }

    private RespuestaPedidoDTO construirRespuesta(Long pedidoId) {
        Pedido p = pedidoRepository.findById(pedidoId)
                .orElseThrow(() -> new NotFoundException("Pedido no encontrado: " + pedidoId));

        PedidoDTO pedidoDTO = new PedidoDTO();
        pedidoDTO.setId(p.getId());
        pedidoDTO.setClienteId(p.getClienteId());
        pedidoDTO.setMoneda(p.getMoneda());
        pedidoDTO.setTotal(p.getTotal());
        pedidoDTO.setEstado(p.getEstado().name());

        List<DetallePedidoDTO> detallesDTO = detallePedidoRepository.findByPedido_Id(pedidoId).stream()
                .map(this::mapDetalleToDTO)
                .collect(Collectors.toList());

        RespuestaPedidoDTO resp = new RespuestaPedidoDTO();
        resp.setPedido(pedidoDTO);
        resp.setDetalles(detallesDTO);
        return resp;
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
