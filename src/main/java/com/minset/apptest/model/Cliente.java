package com.minset.apptest.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cliente", uniqueConstraints = {@UniqueConstraint(name = "uk_cliente_id", columnNames = "cliente_id")})
public class Cliente extends Persona {

    @Column(name="cliente_id", nullable = false, unique = true)
    private Long clienteId;

    @NotBlank(message = "La contrasena no puede estar en blanco")
    @Column(nullable = false)
    private String contrasena;

    @NotNull(message = "El estado no puede estar vacio")
    @Column(nullable = false)
    private Boolean estado;



}
