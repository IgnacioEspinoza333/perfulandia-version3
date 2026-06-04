package com.example.producto_ms.service;

import com.example.producto_ms.dto.ProductoDTO;
import com.example.producto_ms.exception.BusinessException;
import com.example.producto_ms.exception.NotFoundException;
import com.example.producto_ms.model.Categoria;
import com.example.producto_ms.model.Producto;
import com.example.producto_ms.repository.CategoriaRepository;
import com.example.producto_ms.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoServiceImpl(ProductoRepository productoRepository,
                              CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public ProductoDTO crear(ProductoDTO dto) {

        String sku = dto.getSku().trim();

        if (productoRepository.existsBySkuIgnoreCase(sku)) {
            throw new BusinessException("Ya existe un producto con ese sku");
        }

        Categoria cat = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + dto.getCategoriaId()));

        if (Boolean.FALSE.equals(cat.getActiva())) {
            throw new BusinessException("Categoría desactivada");
        }

        Producto p = new Producto();
        p.setNombre(dto.getNombre().trim());
        p.setSku(sku);
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(cat);
        p.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return mapToDTO(productoRepository.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listar() {
        return productoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ProductoDTO obtenerPorId(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));
        return mapToDTO(p);
    }

    @Override
    @Transactional
    public ProductoDTO actualizar(Long id, ProductoDTO dto) {

        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));

        String skuNuevo = dto.getSku().trim();

        if (!p.getSku().equalsIgnoreCase(skuNuevo)
                && productoRepository.existsBySkuIgnoreCase(skuNuevo)) {
            throw new BusinessException("Ya existe un producto con ese sku");
        }

        Categoria cat = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + dto.getCategoriaId()));

        if (Boolean.FALSE.equals(cat.getActiva())) {
            throw new BusinessException("Categoría desactivada");
        }

        p.setCategoria(cat);
        p.setNombre(dto.getNombre().trim());
        p.setSku(skuNuevo);
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());

        if (dto.getActivo() != null) {
            p.setActivo(dto.getActivo());
        }

        return mapToDTO(productoRepository.save(p));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!productoRepository.existsById(id)) {
            throw new NotFoundException("Producto no encontrado: " + id);
        }
        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> buscarPorNombre(String q) {
        return productoRepository.findByNombreContainingIgnoreCase(q)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductoDTO> listarPorCategoria(Long categoriaId) {
        return productoRepository.findByCategoria_Id(categoriaId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProductoDTO activar(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));

        p.setActivo(true);
        return mapToDTO(productoRepository.save(p));
    }

    @Override
    @Transactional
    public ProductoDTO desactivar(Long id) {
        Producto p = productoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Producto no encontrado: " + id));

        p.setActivo(false);
        return mapToDTO(productoRepository.save(p));
    }

    private ProductoDTO mapToDTO(Producto p) {
        return new ProductoDTO(
                p.getId(),
                p.getNombre(),
                p.getSku(),
                p.getPrecio(),
                p.getStock(),
                p.getCategoria().getId(),
                p.getActivo()
        );
    }
}