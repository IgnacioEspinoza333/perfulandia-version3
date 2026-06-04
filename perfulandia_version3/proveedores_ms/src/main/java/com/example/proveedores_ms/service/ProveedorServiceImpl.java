package com.example.proveedores_ms.service;

import com.example.proveedores_ms.client.AbastecimientoClient;
import com.example.proveedores_ms.dto.AbastecimientoDTO;
import com.example.proveedores_ms.dto.ProveedorDTO;
import com.example.proveedores_ms.exception.BusinessException;
import com.example.proveedores_ms.exception.NotFoundException;
import com.example.proveedores_ms.model.Proveedor;
import com.example.proveedores_ms.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final AbastecimientoClient abastecimientoClient;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository,
                                AbastecimientoClient abastecimientoClient) {
        this.proveedorRepository = proveedorRepository;
        this.abastecimientoClient = abastecimientoClient;
    }

    @Override
    public ProveedorDTO crear(ProveedorDTO dto) {
        String email = normalizar(dto.getEmail());

        if (email != null && proveedorRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException("Ya existe un proveedor con ese email");
        }

        Proveedor p = new Proveedor();
        p.setNombre(dto.getNombre().trim());
        p.setEmail(email);
        p.setTelefono(normalizar(dto.getTelefono()));
        p.setDireccion(dto.getDireccion().trim());
        p.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return mapToDTO(proveedorRepository.save(p));
    }

    @Override
    public List<ProveedorDTO> listar() {
        return proveedorRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProveedorDTO obtenerPorId(Long id) {
        Proveedor p = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado: " + id));
        return mapToDTO(p);
    }

    @Override
    @Transactional
    public ProveedorDTO actualizar(Long id, ProveedorDTO dto) {
        Proveedor p = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado: " + id));

        String emailNuevo = normalizar(dto.getEmail());
        if (emailNuevo != null
                && (p.getEmail() == null || !p.getEmail().equalsIgnoreCase(emailNuevo))
                && proveedorRepository.existsByEmailIgnoreCase(emailNuevo)) {
            throw new BusinessException("Ya existe un proveedor con ese email");
        }

        p.setNombre(dto.getNombre().trim());
        p.setEmail(emailNuevo);
        p.setTelefono(normalizar(dto.getTelefono()));
        p.setDireccion(dto.getDireccion().trim());
        if (dto.getActivo() != null) p.setActivo(dto.getActivo());

        return mapToDTO(proveedorRepository.save(p));
    }

    @Override
    public void eliminar(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new NotFoundException("Proveedor no encontrado: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    @Override
    public List<ProveedorDTO> buscarPorNombre(String q) {
        return proveedorRepository.findByNombreContainingIgnoreCase(q).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProveedorDTO activar(Long id) {
        Proveedor p = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado: " + id));
        p.setActivo(true);
        return mapToDTO(proveedorRepository.save(p));
    }

    @Override
    @Transactional
    public ProveedorDTO desactivar(Long id) {
        Proveedor p = proveedorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado: " + id));
        p.setActivo(false);
        return mapToDTO(proveedorRepository.save(p));
    }

    @Override
    public List<AbastecimientoDTO> obtenerAbastecimientos(Long proveedorId) {
        proveedorRepository.findById(proveedorId)
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado: " + proveedorId));
        return abastecimientoClient.listarPorProveedor(proveedorId);
    }

    private ProveedorDTO mapToDTO(Proveedor p) {
        return new ProveedorDTO(
                p.getId(), p.getNombre(), p.getEmail(),
                p.getTelefono(), p.getDireccion(), p.getActivo()
        );
    }

    private String normalizar(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
