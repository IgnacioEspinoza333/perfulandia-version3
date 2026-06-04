package com.example.envios_ms.service;

import com.example.envios_ms.dto.EnvioDTO;
import com.example.envios_ms.exception.BusinessException;
import com.example.envios_ms.exception.NotFoundException;
import com.example.envios_ms.model.Envio;
import com.example.envios_ms.model.EstadoEnvio;
import com.example.envios_ms.repository.EnvioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EnvioServiceImpl implements EnvioService {

    private final EnvioRepository envioRepository;

    public EnvioServiceImpl(EnvioRepository envioRepository) {
        this.envioRepository = envioRepository;
    }

    @Override
    public EnvioDTO crear(EnvioDTO dto) {
        Envio e = new Envio();
        e.setPedidoId(dto.getPedidoId());
        e.setDireccionEntrega(dto.getDireccionEntrega().trim());
        e.setComuna(dto.getComuna().trim());
        e.setCiudad(dto.getCiudad().trim());

        e.setNumeroSeguimiento(generarNumeroSeguimiento());
        e.setEstado(EstadoEnvio.CREADO);

        Instant now = Instant.now();
        e.setFechaCreacion(now);
        e.setUltimaActualizacion(now);

        e.setUbicacionActual(dto.getUbicacionActual() != null ? dto.getUbicacionActual().trim() : "Bodega");

        return mapToDTO(envioRepository.save(e));
    }

    @Override
    public EnvioDTO obtenerPorId(Long id) {
        Envio e = envioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Envío no encontrado: " + id));
        return mapToDTO(e);
    }

    @Override
    public EnvioDTO obtenerPorNumeroSeguimiento(String numeroSeguimiento) {
        Envio e = envioRepository.findByNumeroSeguimiento(numeroSeguimiento)
                .orElseThrow(() -> new NotFoundException("Envío no encontrado para seguimiento: " + numeroSeguimiento));
        return mapToDTO(e);
    }

    @Override
    public List<EnvioDTO> listarPorPedido(Long pedidoId) {
        return envioRepository.findByPedidoId(pedidoId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EnvioDTO actualizarEstado(String numeroSeguimiento, EstadoEnvio nuevoEstado, String ubicacionActual) {
        Envio e = envioRepository.findByNumeroSeguimiento(numeroSeguimiento)
                .orElseThrow(() -> new NotFoundException("Envío no encontrado para seguimiento: " + numeroSeguimiento));

        validarTransicion(e.getEstado(), nuevoEstado);

        e.setEstado(nuevoEstado);

        if (ubicacionActual != null && !ubicacionActual.trim().isEmpty()) {
            e.setUbicacionActual(ubicacionActual.trim());
        }

        e.setUltimaActualizacion(Instant.now());
        return mapToDTO(envioRepository.save(e));
    }

    @Override
    @Transactional
    public EnvioDTO actualizarUbicacion(String numeroSeguimiento, String ubicacionActual) {
        if (ubicacionActual == null || ubicacionActual.trim().isEmpty()) {
            throw new BusinessException("ubicacionActual no puede estar vacía");
        }

        Envio e = envioRepository.findByNumeroSeguimiento(numeroSeguimiento)
                .orElseThrow(() -> new NotFoundException("Envío no encontrado para seguimiento: " + numeroSeguimiento));

        // Opcional: no actualizar ubicación si ya está entregado/cancelado
        if (e.getEstado() == EstadoEnvio.ENTREGADO || e.getEstado() == EstadoEnvio.CANCELADO) {
            throw new BusinessException("No se puede actualizar ubicación cuando el envío está " + e.getEstado());
        }

        e.setUbicacionActual(ubicacionActual.trim());
        e.setUltimaActualizacion(Instant.now());

        return mapToDTO(envioRepository.save(e));
    }

    private void validarTransicion(EstadoEnvio actual, EstadoEnvio nuevo) {
        if (actual == EstadoEnvio.CANCELADO) {
            throw new BusinessException("No se puede cambiar el estado: el envío está CANCELADO");
        }
        if (actual == EstadoEnvio.ENTREGADO) {
            throw new BusinessException("No se puede cambiar el estado: el envío está ENTREGADO");
        }

        // Reglas simples (ajusta si tu negocio lo requiere)
        // Ejemplo: no permitir ir “hacia atrás” desde DESPACHADO a PREPARANDO.
        if (actual == EstadoEnvio.DESPACHADO && (nuevo == EstadoEnvio.PREPARANDO || nuevo == EstadoEnvio.CREADO)) {
            throw new BusinessException("Transición inválida de " + actual + " a " + nuevo);
        }
        if (actual == EstadoEnvio.EN_TRANSITO && (nuevo == EstadoEnvio.PREPARANDO || nuevo == EstadoEnvio.CREADO)) {
            throw new BusinessException("Transición inválida de " + actual + " a " + nuevo);
        }
    }

    private String generarNumeroSeguimiento() {
        // Simple y único: TRK-<8 chars>
        String base = UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
        return "TRK-" + base;
    }

    private EnvioDTO mapToDTO(Envio e) {
        return new EnvioDTO(
                e.getId(),
                e.getPedidoId(),
                e.getNumeroSeguimiento(),
                e.getEstado(),
                e.getDireccionEntrega(),
                e.getComuna(),
                e.getCiudad(),
                e.getUbicacionActual(),
                e.getFechaCreacion(),
                e.getUltimaActualizacion()
        );
    }
}