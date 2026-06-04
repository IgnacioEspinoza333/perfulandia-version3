package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.LoginDTO;
import com.example.usuarios_ms.dto.UsuarioDTO;
import com.example.usuarios_ms.exception.BusinessException;
import com.example.usuarios_ms.model.Usuario;
import com.example.usuarios_ms.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public String login(LoginDTO dto) {
        Usuario u = usuarioRepository.findByEmailIgnoreCase(dto.getEmail().trim())
                .orElseThrow(() -> new BusinessException("Credenciales inválidas"));

        if (Boolean.FALSE.equals(u.getActivo())) {
            throw new BusinessException("Usuario desactivado");
        }

        if (!passwordEncoder.matches(dto.getPassword(), u.getPasswordHash())) {
            throw new BusinessException("Credenciales inválidas");
        }

        // SIN JWT: solo confirmación
        return "Login correcto";
    }

    @Override
    @Transactional
    public UsuarioDTO register(UsuarioDTO dto) {
        String email = dto.getEmail().trim();

        if (usuarioRepository.existsByEmailIgnoreCase(email)) {
            throw new BusinessException("Ya existe un usuario con ese email");
        }

        Usuario u = new Usuario();
        u.setNombre(dto.getNombre().trim());
        u.setEmail(email);
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        u.setActivo(dto.getActivo() != null ? dto.getActivo() : true);

        Usuario saved = usuarioRepository.save(u);
        return new UsuarioDTO(saved.getId(), saved.getNombre(), saved.getEmail(), null, saved.getActivo());
    }
}