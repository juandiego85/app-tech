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
@MappedSuperclass
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personaId;
    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column(nullable = false)
    private String nombre;
    @NotBlank(message = "El genero no puede estar en blanco")
    @Column(nullable = false)
    private String genero;
    @NotNull(message = "La edad no puede estar vacio")
    @Column(nullable = false)
    private Integer edad;
    @NotBlank(message = "La identificacion no puede estar en blanco")
    @Column(nullable = false, unique = true)
    private String identificacion;
    @NotBlank(message = "La direccion no puede estar en blanco")
    @Column(nullable = false)
    private String direccion;
    @NotBlank(message = "El telefono no puede estar en blanco")
    @Column(nullable = false)
    private String telefono;


}
