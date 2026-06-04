package com.example.categorias_ms.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.categorias_ms.dto.CategoriaRequestDTO;
import com.example.categorias_ms.dto.CategoriaResponseDTO;
import com.example.categorias_ms.exception.DuplicateResourceException;
import com.example.categorias_ms.exception.ResourceNotFoundException;
import com.example.categorias_ms.model.Categoria;
import com.example.categorias_ms.repository.CategoriaRepository;

@Service
@Transactional
public class CategoriaServiceImpl implements CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public CategoriaResponseDTO crearCategoria(CategoriaRequestDTO dto) {
        String nombreNormalizado = dto.nombre().trim();

        if (categoriaRepository.existsByNombreIgnoreCase(nombreNormalizado)) {
            throw new DuplicateResourceException("Ya existe una categoría con ese nombre");
        }

        Categoria categoria = new Categoria();
        categoria.setNombre(nombreNormalizado);
        categoria.setDescripcion(dto.descripcion());
        categoria.setEstado(dto.estado().trim().toUpperCase());

        Categoria guardada = categoriaRepository.save(categoria);
        return mapToResponseDTO(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaResponseDTO obtenerCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        return mapToResponseDTO(categoria);
    }

    @Override
    public CategoriaResponseDTO actualizarCategoria(Long id, CategoriaRequestDTO dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));

        String nuevoNombre = dto.nombre().trim();

        categoriaRepository.findByNombreIgnoreCase(nuevoNombre).ifPresent(existente -> {
            if (!existente.getId().equals(id)) {
                throw new DuplicateResourceException("Ya existe otra categoría con ese nombre");
            }
        });

        categoria.setNombre(nuevoNombre);
        categoria.setDescripcion(dto.descripcion());
        categoria.setEstado(dto.estado().trim().toUpperCase());

        Categoria actualizada = categoriaRepository.save(categoria);
        return mapToResponseDTO(actualizada);
    }

    @Override
    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Categoría no encontrada con id: " + id);
        }

        categoriaRepository.deleteById(id);
    }

    private CategoriaResponseDTO mapToResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
                categoria.getId(),
                categoria.getNombre(),
                categoria.getDescripcion(),
                categoria.getEstado()
        );
    }
}