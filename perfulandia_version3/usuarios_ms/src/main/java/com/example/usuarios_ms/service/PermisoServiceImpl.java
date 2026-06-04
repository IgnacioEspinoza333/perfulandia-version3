package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.PermisoDTO;
import com.example.usuarios_ms.exception.BusinessException;
import com.example.usuarios_ms.exception.NotFoundException;
import com.example.usuarios_ms.model.Permiso;
import com.example.usuarios_ms.repository.PermisoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermisoServiceImpl implements PermisoService {

    private final PermisoRepository permisoRepository;

    public PermisoServiceImpl(PermisoRepository permisoRepository) {
        this.permisoRepository = permisoRepository;
    }

    @Override
    @Transactional
    public PermisoDTO crear(PermisoDTO dto) {
        String codigo = dto.getCodigo().trim();

        if (permisoRepository.existsByCodigoIgnoreCase(codigo)) {
            throw new BusinessException("Ya existe un permiso con ese codigo");
        }

        Permiso p = new Permiso();
        p.setCodigo(codigo);
        return mapToDTO(permisoRepository.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermisoDTO> listar() {
        return permisoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PermisoDTO obtenerPorId(Long id) {
        Permiso p = permisoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permiso no encontrado: " + id));
        return mapToDTO(p);
    }

    @Override
    @Transactional
    public PermisoDTO actualizar(Long id, PermisoDTO dto) {
        Permiso p = permisoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permiso no encontrado: " + id));

        String codigoNuevo = dto.getCodigo().trim();
        if (!p.getCodigo().equalsIgnoreCase(codigoNuevo)
                && permisoRepository.existsByCodigoIgnoreCase(codigoNuevo)) {
            throw new BusinessException("Ya existe un permiso con ese codigo");
        }

        p.setCodigo(codigoNuevo);
        return mapToDTO(permisoRepository.save(p));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!permisoRepository.existsById(id)) {
            throw new NotFoundException("Permiso no encontrado: " + id);
        }
        permisoRepository.deleteById(id);
    }

    private PermisoDTO mapToDTO(Permiso p) {
        return new PermisoDTO(p.getId(), p.getCodigo());
    }
}
