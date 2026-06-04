package com.example.pago_ms.service;

import com.example.pago_ms.dto.PagoDTO;

import java.util.List;

public interface PagoService {

    PagoDTO crear(PagoDTO dto);
    List<PagoDTO> listar();
    PagoDTO obtenerPorId(Long id);
    PagoDTO actualizar(Long id, PagoDTO dto);
    void eliminar(Long id);

    List<PagoDTO> listarPorOrden(Long ordenId);

    PagoDTO aprobar(Long id);
    PagoDTO rechazar(Long id);
}