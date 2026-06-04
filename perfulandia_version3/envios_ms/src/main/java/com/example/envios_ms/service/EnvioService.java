package com.example.envios_ms.service;

import com.example.envios_ms.dto.EnvioDTO;
import com.example.envios_ms.model.EstadoEnvio;

import java.util.List;

public interface EnvioService {

    EnvioDTO crear(EnvioDTO dto);

    EnvioDTO obtenerPorId(Long id);

    EnvioDTO obtenerPorNumeroSeguimiento(String numeroSeguimiento);

    List<EnvioDTO> listarPorPedido(Long pedidoId);

    EnvioDTO actualizarEstado(String numeroSeguimiento, EstadoEnvio nuevoEstado, String ubicacionActual);

    EnvioDTO actualizarUbicacion(String numeroSeguimiento, String ubicacionActual);
}