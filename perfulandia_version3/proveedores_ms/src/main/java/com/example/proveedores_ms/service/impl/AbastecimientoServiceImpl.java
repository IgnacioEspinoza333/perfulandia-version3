package com.example.proveedores_ms.service.impl;

import com.example.proveedores_ms.dto.AbastecimientoRequestDto;
import com.example.proveedores_ms.dto.AbastecimientoResponseDto;
import com.example.proveedores_ms.exception.BusinessException;
import com.example.proveedores_ms.exception.ResourceNotFoundException;
import com.example.proveedores_ms.model.Abastecimiento;
import com.example.proveedores_ms.repository.AbastecimientoRepository;
import com.example.proveedores_ms.repository.ProveedorRepository;
import com.example.proveedores_ms.service.AbastecimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AbastecimientoServiceImpl implements AbastecimientoService {

    private final AbastecimientoRepository abastecimientoRepository;
    private final ProveedorRepository proveedorRepository;

    @Override
    public AbastecimientoResponseDto crear(AbastecimientoRequestDto requestDto) {
        validarProveedor(requestDto.getProveedorId());

        Abastecimiento abastecimiento = new Abastecimiento();
        abastecimiento.setFechaCreacion(Instant.now());
        mapToEntity(requestDto, abastecimiento);

        Abastecimiento guardado = abastecimientoRepository.save(abastecimiento);
        return mapToResponse(guardado);
    }

    @Override
    @Transactional(readOnly = true)
    public AbastecimientoResponseDto obtenerPorId(Long id) {
        return mapToResponse(obtenerEntidad(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbastecimientoResponseDto> listar() {
        return abastecimientoRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbastecimientoResponseDto> listarPorProveedor(Long proveedorId) {
        return abastecimientoRepository.findByProveedorId(proveedorId).stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbastecimientoResponseDto> listarPorProducto(Long productoId) {
        return abastecimientoRepository.findByProductoId(productoId).stream().map(this::mapToResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AbastecimientoResponseDto> listarPorEstado(String estado) {
        return abastecimientoRepository.findByEstado(estado).stream().map(this::mapToResponse).toList();
    }

    @Override
    public AbastecimientoResponseDto actualizar(Long id, AbastecimientoRequestDto requestDto) {
        validarProveedor(requestDto.getProveedorId());
        Abastecimiento abastecimiento = obtenerEntidad(id);
        Instant fechaOriginal = abastecimiento.getFechaCreacion();
        mapToEntity(requestDto, abastecimiento);
        abastecimiento.setFechaCreacion(fechaOriginal != null ? fechaOriginal : Instant.now());

        Abastecimiento actualizado = abastecimientoRepository.save(abastecimiento);
        return mapToResponse(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        Abastecimiento abastecimiento = obtenerEntidad(id);
        abastecimientoRepository.delete(abastecimiento);
    }

    private void validarProveedor(Long proveedorId) {
        if (!proveedorRepository.existsById(proveedorId)) {
            throw new BusinessException("No existe un proveedor con id: " + proveedorId);
        }
    }

    private Abastecimiento obtenerEntidad(Long id) {
        return abastecimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Abastecimiento no encontrado con id: " + id));
    }

    private void mapToEntity(AbastecimientoRequestDto dto, Abastecimiento abastecimiento) {
        abastecimiento.setProveedorId(dto.getProveedorId());
        abastecimiento.setProductoId(dto.getProductoId());
        abastecimiento.setCantidad(dto.getCantidad());
        abastecimiento.setEstado(dto.getEstado());
    }

    private AbastecimientoResponseDto mapToResponse(Abastecimiento abastecimiento) {
        return new AbastecimientoResponseDto(
                abastecimiento.getId(),
                abastecimiento.getProveedorId(),
                abastecimiento.getProductoId(),
                abastecimiento.getCantidad(),
                abastecimiento.getEstado(),
                abastecimiento.getFechaCreacion(),
                abastecimiento.getVersion()
        );
    }
}
