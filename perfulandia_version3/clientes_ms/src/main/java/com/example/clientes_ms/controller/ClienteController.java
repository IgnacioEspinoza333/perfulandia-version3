package com.example.clientes_ms.controller;

import com.example.clientes_ms.dto.ClienteDTO;
import com.example.clientes_ms.service.ClienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@Validated
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    // Registro
    @PostMapping
    public ClienteDTO crear(@Valid @RequestBody ClienteDTO dto) {
        return clienteService.crear(dto);
    }

    @GetMapping
    public List<ClienteDTO> listar() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public ClienteDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return clienteService.obtener(id);
    }

    // Actualización de datos
    @PutMapping("/{id}")
    public ClienteDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                                 @Valid @RequestBody ClienteDTO dto) {
        return clienteService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        clienteService.eliminar(id);
    }

    // Búsqueda simple
    @GetMapping("/buscar")
    public List<ClienteDTO> buscar(@RequestParam("q") @NotBlank(message = "q no puede estar vacío") String q) {
        return clienteService.buscar(q);
    }

    // Consulta de perfil (cliente + direcciones)
    @GetMapping("/{id}/perfil")
    public ClienteDTO perfil(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return clienteService.perfil(id);
    }
}