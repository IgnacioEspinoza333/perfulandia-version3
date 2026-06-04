package com.example.usuarios_ms.controller;

import com.example.usuarios_ms.dto.UsuarioDTO;
import com.example.usuarios_ms.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@Validated
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioDTO crear(@Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.crear(dto);
    }

    @GetMapping
    public List<UsuarioDTO> listar() {
        return usuarioService.listar();
    }

    @GetMapping("/{id}")
    public UsuarioDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return usuarioService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public UsuarioDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                                 @Valid @RequestBody UsuarioDTO dto) {
        return usuarioService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        usuarioService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<UsuarioDTO> buscar(@RequestParam("q") @NotBlank(message = "q no puede estar vacío") String q) {
        return usuarioService.buscarPorNombre(q);
    }

    @PatchMapping("/{id}/activar")
    public UsuarioDTO activar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return usuarioService.activar(id);
    }

    @PatchMapping("/{id}/desactivar")
    public UsuarioDTO desactivar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return usuarioService.desactivar(id);
    }
}