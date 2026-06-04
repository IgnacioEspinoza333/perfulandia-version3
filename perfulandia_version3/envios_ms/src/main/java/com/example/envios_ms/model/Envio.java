package com.example.envios_ms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.Instant;

@Entity
@Table(
    name = "envios",
    uniqueConstraints = @UniqueConstraint(name = "uk_envio_numero_seguimiento", columnNames = "numero_seguimiento")
)
@Getter
@Setter
@NoArgsConstructor
public class Envio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Referencia a otro microservicio (pedido/venta). No usamos FK real; solo ID.
    @NotNull
    @Column(name = "pedido_id", nullable = false)
    private Long pedidoId;

    @Column(name = "numero_seguimiento", nullable = false, unique = true, length = 40)
    private String numeroSeguimiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEnvio estado;

    // Datos mínimos de entrega
    @Column(nullable = false, length = 120)
    private String direccionEntrega;

    @Column(nullable = false, length = 60)
    private String comuna;

    @Column(nullable = false, length = 60)
    private String ciudad;

    // Seguimiento
    @Column(length = 120)
    private String ubicacionActual;

    @Column(name = "fecha_creacion", nullable = false)
    private Instant fechaCreacion;

    @Column(name = "ultima_actualizacion", nullable = false)
    private Instant ultimaActualizacion;

    @Version
    private Long version;
}