package com.example.producto_ms.service;

import com.example.producto_ms.dto.CategoriaDTO;
import com.example.producto_ms.exception.BusinessException;
import com.example.producto_ms.exception.NotFoundException;
import com.example.producto_ms.model.Categoria;
import com.example.producto_ms.repository.CategoriaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    @Transactional
    public CategoriaDTO crear(CategoriaDTO dto) {
        String nombre = dto.getNombre().trim();

        if (categoriaRepository.existsByNombreIgnoreCase(nombre)) {
            throw new BusinessException("Ya existe una categoría con ese nombre");
        }

        Categoria c = new Categoria();
        c.setNombre(nombre);
        c.setActiva(dto.getActiva() != null ? dto.getActiva() : true);

        return mapToDTO(categoriaRepository.save(c));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> listar() {
        return categoriaRepository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO obtenerPorId(Long id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + id));
        return mapToDTO(c);
    }

    @Override
    @Transactional
    public CategoriaDTO actualizar(Long id, CategoriaDTO dto) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + id));

        String nombreNuevo = dto.getNombre().trim();
        if (!c.getNombre().equalsIgnoreCase(nombreNuevo)
                && categoriaRepository.existsByNombreIgnoreCase(nombreNuevo)) {
            throw new BusinessException("Ya existe una categoría con ese nombre");
        }

        c.setNombre(nombreNuevo);
        if (dto.getActiva() != null) c.setActiva(dto.getActiva());

        return mapToDTO(categoriaRepository.save(c));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new NotFoundException("Categoría no encontrada: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> buscarPorNombre(String q) {
        return categoriaRepository.findByNombreContainingIgnoreCase(q).stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    @Transactional
    public CategoriaDTO activar(Long id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + id));
        c.setActiva(true);
        return mapToDTO(categoriaRepository.save(c));
    }

    @Override
    @Transactional
    public CategoriaDTO desactivar(Long id) {
        Categoria c = categoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Categoría no encontrada: " + id));
        c.setActiva(false);
        return mapToDTO(categoriaRepository.save(c));
    }

    private CategoriaDTO mapToDTO(Categoria c) {
        return new CategoriaDTO(c.getId(), c.getNombre(), c.getActiva());
    }
}