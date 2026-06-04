package com.example.clientes_ms.service;

import com.example.clientes_ms.dto.ClienteDTO;
import com.example.clientes_ms.dto.DireccionDTO;
import com.example.clientes_ms.exception.BusinessException;
import com.example.clientes_ms.exception.NotFoundException;
import com.example.clientes_ms.model.Cliente;
import com.example.clientes_ms.repository.ClienteRepository;
import com.example.clientes_ms.repository.DireccionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final DireccionRepository direccionRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository, DireccionRepository direccionRepository) {
        this.clienteRepository = clienteRepository;
        this.direccionRepository = direccionRepository;
    }

    @Override
    public ClienteDTO crear(ClienteDTO dto) {
        String email = dto.getEmail().trim().toLowerCase();
        if (clienteRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException("Ya existe un cliente con ese email");
        }

        Cliente c = new Cliente();
        c.setNombres(dto.getNombres().trim());
        c.setApellidos(dto.getApellidos().trim());
        c.setEmail(email);
        c.setTelefono(normalizar(dto.getTelefono()));
        c.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return mapToDTO(clienteRepository.save(c));
    }

    @Override
    public List<ClienteDTO> listar() {
        return clienteRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO obtener(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));
        return mapToDTO(c);
    }

    @Override
    @Transactional
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));

        String emailNuevo = dto.getEmail().trim().toLowerCase();
        if (!c.getEmail().equalsIgnoreCase(emailNuevo) && clienteRepository.existsByEmailIgnoreCase(emailNuevo)) {
            throw new BusinessException("Ya existe un cliente con ese email");
        }

        c.setNombres(dto.getNombres().trim());
        c.setApellidos(dto.getApellidos().trim());
        c.setEmail(emailNuevo);
        c.setTelefono(normalizar(dto.getTelefono()));
        if (dto.getActivo() != null) c.setActivo(dto.getActivo());

        return mapToDTO(clienteRepository.save(c));
    }

    @Override
    public void eliminar(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NotFoundException("Cliente no encontrado: " + id);
        }
        clienteRepository.deleteById(id);
    }

    @Override
    public List<ClienteDTO> buscar(String q) {
        return clienteRepository
                .findByNombresContainingIgnoreCaseOrApellidosContainingIgnoreCase(q, q)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO perfil(Long id) {
        Cliente c = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));

        ClienteDTO dto = mapToDTO(c);

        List<DireccionDTO> direcciones = direccionRepository.findByCliente_Id(id)
                .stream()
                .map(d -> new DireccionDTO(
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
                ))
                .collect(Collectors.toList());

        dto.setDirecciones(direcciones);
        return dto;
    }

    private ClienteDTO mapToDTO(Cliente c) {
        return new ClienteDTO(
                c.getId(),
                c.getNombres(),
                c.getApellidos(),
                c.getEmail(),
                c.getTelefono(),
                c.getActivo(),
                null
        );
    }

    private String normalizar(String s) {
        if (s == null) return null;
        String t = s.trim();
        return t.isEmpty() ? null : t;
    }
}
