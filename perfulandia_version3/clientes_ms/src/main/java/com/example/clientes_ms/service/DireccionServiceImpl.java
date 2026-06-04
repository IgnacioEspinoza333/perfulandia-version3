package com.example.clientes_ms.service;

import com.example.clientes_ms.dto.DireccionDTO;
import com.example.clientes_ms.exception.NotFoundException;
import com.example.clientes_ms.model.Cliente;
import com.example.clientes_ms.model.Direccion;
import com.example.clientes_ms.repository.ClienteRepository;
import com.example.clientes_ms.repository.DireccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DireccionServiceImpl implements DireccionService {

    private final DireccionRepository direccionRepository;
    private final ClienteRepository clienteRepository;

    public DireccionServiceImpl(DireccionRepository direccionRepository, ClienteRepository clienteRepository) {
        this.direccionRepository = direccionRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public DireccionDTO crearParaCliente(Long clienteId, DireccionDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + clienteId));

        Direccion d = new Direccion();
        d.setCliente(cliente);
        d.setCalle(dto.getCalle().trim());
        d.setNumero(dto.getNumero().trim());
        d.setDepto(normalizar(dto.getDepto()));
        d.setComuna(dto.getComuna().trim());
        d.setCiudad(dto.getCiudad().trim());
        d.setCodigoPostal(normalizar(dto.getCodigoPostal()));
        d.setReferencia(normalizar(dto.getReferencia()));

        boolean quierePrincipal = dto.getPrincipal() != null && dto.getPrincipal();
        d.setPrincipal(quierePrincipal);

        Direccion guardada = direccionRepository.save(d);

        // Si esta nueva es principal, desmarcamos las otras del cliente
        if (quierePrincipal) {
            desmarcarOtrasPrincipales(clienteId, guardada.getId());
        } else {
            // Si el cliente NO tiene principal todavía, la primera dirección será principal automáticamente
            if (direccionRepository.findByCliente_IdAndPrincipalTrue(clienteId).isEmpty()) {
                guardada.setPrincipal(true);
                guardada = direccionRepository.save(guardada);
            }
        }

        return mapToDTO(guardada);
    }

    @Override
    public List<DireccionDTO> listarPorCliente(Long clienteId) {
        return direccionRepository.findByCliente_Id(clienteId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DireccionDTO obtener(Long id) {
        Direccion d = direccionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada: " + id));
        return mapToDTO(d);
    }

    @Override
    @Transactional
    public DireccionDTO actualizar(Long id, DireccionDTO dto) {
        Direccion d = direccionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada: " + id));

        d.setCalle(dto.getCalle().trim());
        d.setNumero(dto.getNumero().trim());
        d.setDepto(normalizar(dto.getDepto()));
        d.setComuna(dto.getComuna().trim());
        d.setCiudad(dto.getCiudad().trim());
        d.setCodigoPostal(normalizar(dto.getCodigoPostal()));
        d.setReferencia(normalizar(dto.getReferencia()));

        boolean quierePrincipal = dto.getPrincipal() != null && dto.getPrincipal();
        if (quierePrincipal) {
            d.setPrincipal(true);
        }

        Direccion guardada = direccionRepository.save(d);

        if (quierePrincipal) {
            desmarcarOtrasPrincipales(d.getCliente().getId(), guardada.getId());
        }

        return mapToDTO(guardada);
    }

    @Override
    public void eliminar(Long id) {
        if (!direccionRepository.existsById(id)) {
            throw new NotFoundException("Dirección no encontrada: " + id);
        }
        direccionRepository.deleteById(id);
    }

    @Override
    @Transactional
    public DireccionDTO marcarPrincipal(Long id) {
        Direccion d = direccionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Dirección no encontrada: " + id));

        d.setPrincipal(true);
        Direccion guardada = direccionRepository.save(d);

        desmarcarOtrasPrincipales(d.getCliente().getId(), guardada.getId());

        return mapToDTO(guardada);
    }

    private void desmarcarOtrasPrincipales(Long clienteId, Long direccionPrincipalId) {
        List<Direccion> todas = direccionRepository.findByCliente_Id(clienteId);
        for (Direccion dir : todas) {
            if (!dir.getId().equals(direccionPrincipalId) && Boolean.TRUE.equals(dir.getPrincipal())) {
                dir.setPrincipal(false);
                direccionRepository.save(dir);
            }
        }
    }

    private DireccionDTO mapToDTO(Direccion d) {
        return new DireccionDTO(
                d.getId(),
                d.getCliente().getId(),
                d.getCalle(),
                d.getNumero(),
                d.getDepto(),
                d.getComuna(),
                d.getCiudad(),
                d.getCodigoPostal(),
                d.getReferencia(),
                d.getPrincipal()
        );
    }

    private String normalizar(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}