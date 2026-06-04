package com.example.usuarios_ms.service;

import com.example.usuarios_ms.dto.LoginDTO;
import com.example.usuarios_ms.dto.UsuarioDTO;

public interface AuthService {
    String login(LoginDTO dto);
    UsuarioDTO register(UsuarioDTO dto);
}