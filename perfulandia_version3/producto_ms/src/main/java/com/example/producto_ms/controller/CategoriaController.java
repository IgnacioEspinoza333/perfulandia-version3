package com.example.producto_ms.controller;

import com.example.producto_ms.dto.CategoriaDTO;
import com.example.producto_ms.service.CategoriaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@Validated
public class CategoriaController {

    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    @PostMapping
    public CategoriaDTO crear(@Valid @RequestBody CategoriaDTO dto) {
        return categoriaService.crear(dto);
    }

    @GetMapping
    public List<CategoriaDTO> listar() {
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    public CategoriaDTO obtener(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return categoriaService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public CategoriaDTO actualizar(@PathVariable @Positive(message = "id debe ser positivo") Long id,
                                   @Valid @RequestBody CategoriaDTO dto) {
        return categoriaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        categoriaService.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<CategoriaDTO> buscar(@RequestParam("q") @NotBlank(message = "q no puede estar vacío") String q) {
        return categoriaService.buscarPorNombre(q);
    }

    @PatchMapping("/{id}/activar")
    public CategoriaDTO activar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return categoriaService.activar(id);
    }

    @PatchMapping("/{id}/desactivar")
    public CategoriaDTO desactivar(@PathVariable @Positive(message = "id debe ser positivo") Long id) {
        return categoriaService.desactivar(id);
    }
}