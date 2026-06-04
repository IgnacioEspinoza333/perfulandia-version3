package com.example.usuarios_ms.controller;

import com.example.usuarios_ms.model.Empleado;
import com.example.usuarios_ms.service.EmpleadoService;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
@Validated
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @PostMapping("/usuario/{usuarioId}")
    public Empleado crearDesdeUsuario(@PathVariable @Positive(message = "usuarioId debe ser positivo") Long usuarioId) {
        return empleadoService.crearDesdeUsuario(usuarioId);
    }

    @GetMapping
    public List<Empleado> listar() {
        return empleadoService.listar();
    }

    @GetMapping("/{id}")
    public Empleado obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return empleadoService.obtenerPorId(id);
    }

    @PatchMapping("/{id}/activar")
    public Empleado activar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return empleadoService.activar(id);
    }

    @PatchMapping("/{id}/desactivar")
    public Empleado desactivar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return empleadoService.desactivar(id);
    }
}