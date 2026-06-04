package com.example.usuarios_ms.controller;

import com.example.usuarios_ms.dto.LoginDTO;
import com.example.usuarios_ms.dto.UsuarioDTO;
import com.example.usuarios_ms.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDTO dto) {
        return authService.login(dto);
    }

    @PostMapping("/register")
    public UsuarioDTO register(@Valid @RequestBody UsuarioDTO dto) {
        return authService.register(dto);
    }
}
