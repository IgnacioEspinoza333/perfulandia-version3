package com.example.inventario_ms.service;

import com.example.inventario_ms.dto.InventarioDTO;
import com.example.inventario_ms.dto.StockDTO;

import java.util.List;

public interface InventarioService {

    InventarioDTO crear(InventarioDTO dto);

    List<InventarioDTO> listar();

    InventarioDTO obtenerPorProductoId(Long productoId);

    InventarioDTO actualizarStock(Long productoId, Integer nuevoStock);

    InventarioDTO aumentarStock(StockDTO dto);

    InventarioDTO disminuirStock(StockDTO dto);

    boolean disponible(Long productoId, Integer cantidad);
}