package com.example.proveedores_ms.service;

import com.example.proveedores_ms.dto.AbastecimientoDTO;
import com.example.proveedores_ms.exception.BusinessException;
import com.example.proveedores_ms.exception.NotFoundException;
import com.example.proveedores_ms.model.Abastecimiento;
import com.example.proveedores_ms.model.Proveedor;
import com.example.proveedores_ms.repository.AbastecimientoRepository;
import com.example.proveedores_ms.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AbastecimientoServiceImpl implements AbastecimientoService {

    private final AbastecimientoRepository abastecimientoRepository;
    private final ProveedorRepository proveedorRepository;

    public AbastecimientoServiceImpl(AbastecimientoRepository abastecimientoRepository,
                                     ProveedorRepository proveedorRepository) {
        this.abastecimientoRepository = abastecimientoRepository;
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public AbastecimientoDTO crear(AbastecimientoDTO dto) {
        Proveedor p = proveedorRepository.findById(dto.getProveedorId())
                .orElseThrow(() -> new NotFoundException("Proveedor no encontrado: " + dto.getProveedorId()));

        if (Boolean.FALSE.equals(p.getActivo())) {
            throw new BusinessException("Proveedor desactivado: no puede generar abastecimientos");
        }

        Abastecimiento a = new Abastecimiento();
        a.setProveedorId(dto.getProveedorId());
        a.setProductoId(dto.getProductoId());
        a.setCantidad(dto.getCantidad());
        a.setEstado("PENDIENTE");
        a.setFechaCreacion(Instant.now());

        return mapToDTO(abastecimientoRepository.save(a));
    }

    @Override
    public AbastecimientoDTO obtener(Long id) {
        Abastecimiento a = abastecimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Abastecimiento no encontrado: " + id));
        return mapToDTO(a);
    }

    @Override
    public List<AbastecimientoDTO> listar() {
        return abastecimientoRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbastecimientoDTO> listarPorProveedor(Long proveedorId) {
        return abastecimientoRepository.findByProveedorId(proveedorId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<AbastecimientoDTO> listarPorProducto(Long productoId) {
        return abastecimientoRepository.findByProductoId(productoId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AbastecimientoDTO marcarCompletado(Long id) {
        Abastecimiento a = abastecimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Abastecimiento no encontrado: " + id));

        if ("CANCELADO".equals(a.getEstado())) {
            throw new BusinessException("No se puede completar un abastecimiento CANCELADO");
        }

        a.setEstado("COMPLETADO");
        return mapToDTO(abastecimientoRepository.save(a));
    }

    @Override
    @Transactional
    public AbastecimientoDTO cancelar(Long id) {
        Abastecimiento a = abastecimientoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Abastecimiento no encontrado: " + id));

        if ("COMPLETADO".equals(a.getEstado())) {
            throw new BusinessException("No se puede cancelar un abastecimiento COMPLETADO");
        }

        a.setEstado("CANCELADO");
        return mapToDTO(abastecimientoRepository.save(a));
    }

    private AbastecimientoDTO mapToDTO(Abastecimiento a) {
        return new AbastecimientoDTO(
                a.getId(),
                a.getProveedorId(),
                a.getProductoId(),
                a.getCantidad(),
                a.getEstado(),
                a.getFechaCreacion()
        );
    }
}