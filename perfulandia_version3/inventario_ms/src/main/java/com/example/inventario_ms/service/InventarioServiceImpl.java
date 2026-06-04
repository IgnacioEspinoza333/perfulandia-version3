package com.example.inventario_ms.service;

import com.example.inventario_ms.dto.InventarioDTO;
import com.example.inventario_ms.dto.StockDTO;
import com.example.inventario_ms.exception.BusinessException;
import com.example.inventario_ms.exception.NotFoundException;
import com.example.inventario_ms.model.Inventario;
import com.example.inventario_ms.repository.InventarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioServiceImpl implements InventarioService {

    private final InventarioRepository inventarioRepository;

    public InventarioServiceImpl(InventarioRepository inventarioRepository) {
        this.inventarioRepository = inventarioRepository;
    }

    @Override
    public InventarioDTO crear(InventarioDTO dto) {
        if (inventarioRepository.existsByProductoId(dto.getProductoId())) {
            throw new BusinessException("Ya existe inventario para productoId: " + dto.getProductoId());
        }
        Inventario inv = new Inventario();
        inv.setProductoId(dto.getProductoId());
        inv.setStock(dto.getStock());
        return mapToDTO(inventarioRepository.save(inv));
    }

    @Override
    public List<InventarioDTO> listar() {
        return inventarioRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventarioDTO obtenerPorProductoId(Long productoId) {
        Inventario inv = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado para productoId: " + productoId));
        return mapToDTO(inv);
    }

    @Override
    @Transactional
    public InventarioDTO actualizarStock(Long productoId, Integer nuevoStock) {
        if (nuevoStock < 0) {
            throw new BusinessException("El stock no puede ser negativo");
        }

        Inventario inv = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado para productoId: " + productoId));

        inv.setStock(nuevoStock);
        return mapToDTO(inventarioRepository.save(inv));
    }

    @Override
    @Transactional
    public InventarioDTO aumentarStock(StockDTO dto) {
        Inventario inv = inventarioRepository.findByProductoId(dto.getProductoId())
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado para productoId: " + dto.getProductoId()));

        inv.setStock(inv.getStock() + dto.getCantidad());
        return mapToDTO(inventarioRepository.save(inv));
    }

    @Override
    @Transactional
    public InventarioDTO disminuirStock(StockDTO dto) {
        Inventario inv = inventarioRepository.findByProductoId(dto.getProductoId())
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado para productoId: " + dto.getProductoId()));

        int stockActual = inv.getStock();
        int nuevoStock = stockActual - dto.getCantidad();

        if (nuevoStock < 0) {
            throw new BusinessException("Stock insuficiente. Stock actual: " + stockActual
                    + ", intento disminuir: " + dto.getCantidad());
        }

        inv.setStock(nuevoStock);
        return mapToDTO(inventarioRepository.save(inv));
    }

    @Override
    public boolean disponible(Long productoId, Integer cantidad) {
        Inventario inv = inventarioRepository.findByProductoId(productoId)
                .orElseThrow(() -> new NotFoundException("Inventario no encontrado para productoId: " + productoId));
        return inv.getStock() >= cantidad;
    }

    private InventarioDTO mapToDTO(Inventario inv) {
        return new InventarioDTO(inv.getId(), inv.getProductoId(), inv.getStock());
    }
}