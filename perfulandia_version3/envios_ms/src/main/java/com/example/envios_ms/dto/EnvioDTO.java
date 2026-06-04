package com.example.envios_ms.dto;

import com.example.envios_ms.model.EstadoEnvio;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnvioDTO {

    private Long id;

    @NotNull(message = "pedidoId es obligatorio")
    @Positive(message = "pedidoId debe ser positivo")
    private Long pedidoId;

    // En crear, puede venir null (lo genera el sistema). En respuesta, se devuelve.
    private String numeroSeguimiento;

    private EstadoEnvio estado;

    @NotBlank(message = "direccionEntrega es obligatoria")
    @Size(min = 5, max = 120, message = "direccionEntrega debe tener entre 5 y 120 caracteres")
    private String direccionEntrega;

    @NotBlank(message = "comuna es obligatoria")
    @Size(min = 2, max = 60, message = "comuna debe tener entre 2 y 60 caracteres")
    private String comuna;

    @NotBlank(message = "ciudad es obligatoria")
    @Size(min = 2, max = 60, message = "ciudad debe tener entre 2 y 60 caracteres")
    private String ciudad;

    @Size(max = 120, message = "ubicacionActual no puede exceder 120 caracteres")
    private String ubicacionActual;

    private Instant fechaCreacion;
    private Instant ultimaActualizacion;
}