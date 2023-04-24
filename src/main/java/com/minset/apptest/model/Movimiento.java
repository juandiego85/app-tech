package com.minset.apptest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movimiento",uniqueConstraints =
        {@UniqueConstraint(columnNames = {"fecha", "tipo_movimiento", "valor", "saldo"})})
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movimientoId;

    @NotNull(message = "La fehca no puede estar vacio")
    @Column(nullable = false)
    @JsonSerialize(using= ToStringSerializer.class)
    private LocalDateTime fecha;
    @NotBlank(message = "El tipo de movimiento no puede estar en blanco")
    @Column(name = "tipo_movimiento", nullable = false)
    private String tipoMovimiento;
    @NotNull(message = "El valor no puede estar vacio")
    @Column(nullable = false)
    private BigDecimal valor;
    @NotNull(message = "El saldo no puede estar vacio")
    @Column(nullable = false)
    private BigDecimal saldo;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cuenta_id", nullable = false)
    private Cuenta cuenta;

}
