package com.example.pago_ms.service;

import com.example.pago_ms.dto.PagoDTO;
import com.example.pago_ms.exception.BusinessException;
import com.example.pago_ms.exception.NotFoundException;
import com.example.pago_ms.model.EstadoPago;
import com.example.pago_ms.model.Pago;
import com.example.pago_ms.repository.PagoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;

    public PagoServiceImpl(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    @Override
    @Transactional
    public PagoDTO crear(PagoDTO dto) {

        String referencia = dto.getReferencia().trim();

        if (pagoRepository.existsByReferenciaIgnoreCase(referencia)) {
            throw new BusinessException("Ya existe un pago con esa referencia");
        }

        Pago p = new Pago();
        p.setOrdenId(dto.getOrdenId());
        p.setMonto(dto.getMonto());
        p.setMoneda(dto.getMoneda().trim().toUpperCase());
        p.setMetodo(dto.getMetodo());
        p.setReferencia(referencia);
        p.setEstado(EstadoPago.PENDIENTE);

        return mapToDTO(pagoRepository.save(p));
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> listar() {
        return pagoRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PagoDTO obtenerPorId(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado: " + id));
        return mapToDTO(p);
    }

    @Override
    @Transactional
    public PagoDTO actualizar(Long id, PagoDTO dto) {

        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado: " + id));

        if (p.getEstado() != EstadoPago.PENDIENTE) {
            throw new BusinessException("Solo se puede actualizar un pago en estado PENDIENTE");
        }

        String refNueva = dto.getReferencia().trim();

        if (!p.getReferencia().equalsIgnoreCase(refNueva)
                && pagoRepository.existsByReferenciaIgnoreCase(refNueva)) {
            throw new BusinessException("Ya existe un pago con esa referencia");
        }

        p.setOrdenId(dto.getOrdenId());
        p.setMonto(dto.getMonto());
        p.setMoneda(dto.getMoneda().trim().toUpperCase());
        p.setMetodo(dto.getMetodo());
        p.setReferencia(refNueva);

        return mapToDTO(pagoRepository.save(p));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!pagoRepository.existsById(id)) {
            throw new NotFoundException("Pago no encontrado: " + id);
        }
        pagoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PagoDTO> listarPorOrden(Long ordenId) {
        return pagoRepository.findByOrdenId(ordenId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public PagoDTO aprobar(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado: " + id));

        if (p.getEstado() == EstadoPago.APROBADO) {
            return mapToDTO(p);
        }
        if (p.getEstado() == EstadoPago.RECHAZADO) {
            throw new BusinessException("No se puede aprobar un pago RECHAZADO");
        }

        p.setEstado(EstadoPago.APROBADO);
        return mapToDTO(pagoRepository.save(p));
    }

    @Override
    @Transactional
    public PagoDTO rechazar(Long id) {
        Pago p = pagoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pago no encontrado: " + id));

        if (p.getEstado() == EstadoPago.RECHAZADO) {
            return mapToDTO(p);
        }
        if (p.getEstado() == EstadoPago.APROBADO) {
            throw new BusinessException("No se puede rechazar un pago APROBADO");
        }

        p.setEstado(EstadoPago.RECHAZADO);
        return mapToDTO(pagoRepository.save(p));
    }

    private PagoDTO mapToDTO(Pago p) {
        return new PagoDTO(
                p.getId(),
                p.getOrdenId(),
                p.getMonto(),
                p.getMoneda(),
                p.getMetodo(),
                p.getReferencia(),
                p.getEstado().name()
        );
    }
}