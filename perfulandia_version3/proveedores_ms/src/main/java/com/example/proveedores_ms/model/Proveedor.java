package com.example.proveedores_ms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
    name = "proveedores",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_proveedor_email", columnNames = "email")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, max = 80)
    @Column(nullable = false, length = 80)
    private String nombre;

    @Email
    @Size(max = 120)
    @Column(length = 120)
    private String email;

    @Size(min = 6, max = 20)
    @Column(length = 20)
    private String telefono;

    @NotBlank
    @Size(min = 5, max = 160)
    @Column(nullable = false, length = 160)
    private String direccion;

    @Column(nullable = false)
    private Boolean activo = true;

    @Version
    private Long version;
}