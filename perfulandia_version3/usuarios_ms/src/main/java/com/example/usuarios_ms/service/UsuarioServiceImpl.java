package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.UsuarioDTO;
import com.example.usuarios_ms.exception.BusinessException;
import com.example.usuarios_ms.exception.NotFoundException;
import com.example.usuarios_ms.model.Usuario;
import com.example.usuarios_ms.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UsuarioDTO crear(UsuarioDTO dto) {
        String email = dto.getEmail().trim();

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }

        Usuario u = new Usuario();
        u.setNombre(dto.getNombre().trim());
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        u.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        return mapToDTO(usuarioRepository.save(u));
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        return mapToDTO(u);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizar(Long id, UsuarioDTO dto) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));

        String emailNuevo = dto.getEmail().trim();
        if (!u.getEmail().equalsIgnoreCase(emailNuevo)
                && usuarioRepository.existsByEmailIgnoreCase(emailNuevo)) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }

        u.setNombre(dto.getNombre().trim());
        u.setEmail(emailNuevo);

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getActivo() != null) u.setActivo(dto.getActivo());

        return mapToDTO(usuarioRepository.save(u));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> buscarPorNombre(String q) {
        return usuarioRepository.findByNombreContainingIgnoreCase(q).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UsuarioDTO activar(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        u.setActivo(true);
        return mapToDTO(usuarioRepository.save(u));
    }

    @Override
    @Transactional
    public UsuarioDTO desactivar(Long id) {
        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado: " + id));
        u.setActivo(false);
        return mapToDTO(usuarioRepository.save(u));
    }

    private UsuarioDTO mapToDTO(Usuario u) {
        return new UsuarioDTO(u.getId(), u.getNombre(), u.getEmail(), null, u.getActivo());
    }
}