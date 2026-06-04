package com.example.proveedores_ms.service.impl;

import com.example.proveedores_ms.dto.ProveedorRequestDto;
import com.example.proveedores_ms.dto.ProveedorResponseDto;
import com.example.proveedores_ms.exception.BusinessException;
import com.example.proveedores_ms.exception.ResourceNotFoundException;
import com.example.proveedores_ms.model.Proveedor;
import com.example.proveedores_ms.repository.ProveedorRepository;
import com.example.proveedores_ms.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;

    @Override
    public ProveedorResponseDto crear(ProveedorRequestDto requestDto) {
        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank() && proveedorRepository.existsByEmail(requestDto.getEmail())) {
            throw new BusinessException("Ya existe un proveedor con el email indicado");
        }

        Proveedor proveedor = new Proveedor();
        mapToEntity(requestDto, proveedor);
        Proveedor guardado = proveedorRepository.save(proveedor);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorResponseDto obtenerPorId(Long id) {
        return mapToResponse(obtenerEntidad(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorResponseDto> listar() {
        return proveedorRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    public ProveedorResponseDto actualizar(Long id, ProveedorRequestDto requestDto) {
        Proveedor proveedor = obtenerEntidad(id);

        if (requestDto.getEmail() != null && !requestDto.getEmail().isBlank()) {
            proveedorRepository.findByEmail(requestDto.getEmail())
                    .filter(existente -> !existente.getId().equals(id))
                    .ifPresent(existente -> {
                        throw new BusinessException("Ya existe otro proveedor con el email indicado");
                    });
        }

        mapToEntity(requestDto, proveedor);
        Proveedor actualizado = proveedorRepository.save(proveedor);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Proveedor proveedor = obtenerEntidad(id);
        proveedorRepository.delete(proveedor);
    }

    private Proveedor obtenerEntidad(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con id: " + id));
    }

    private void mapToEntity(ProveedorRequestDto dto, Proveedor proveedor) {
        proveedor.setNombre(dto.getNombre());
        proveedor.setEmail(dto.getEmail());
        proveedor.setTelefono(dto.getTelefono());
        proveedor.setDireccion(dto.getDireccion());
        proveedor.setActivo(dto.getActivo() != null ? dto.getActivo() : Boolean.TRUE);
    }

    private ProveedorResponseDto mapToResponse(Proveedor proveedor) {
        return new ProveedorResponseDto(
                proveedor.getId(),
                proveedor.getNombre(),
                proveedor.getEmail(),
                proveedor.getTelefono(),
                proveedor.getDireccion(),
                proveedor.getActivo(),
                proveedor.getVersion()
        );
    }
}
