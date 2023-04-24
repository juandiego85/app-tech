package com.minset.apptest.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "cuenta", uniqueConstraints = {@UniqueConstraint(name = "uk_numero_cuenta", columnNames = "numero_cuenta")})
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cuentaId;

    @NotBlank(message = "El numero de cuenta no puede estar en blanco")
    @Column(name="numero_cuenta", nullable = false, unique = true)
    private String numeroCuenta;

    @NotBlank(message = "El tipo de cuenta no puede estar en blanco")
    @Column(nullable = false)
    private String tipoCuenta;
    @NotNull(message = "El saldo inicial no puede estar vacio")
    @Column(nullable = false)
    private BigDecimal saldoInicial;
    @NotNull(message = "El estado de la cuenta no puede estar vacío")
    @Column(nullable = false)
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "persona_id", nullable = false)
    private Cliente cliente;


  @OneToMany(mappedBy="cuenta",cascade={CascadeType.PERSIST, CascadeType.MERGE,CascadeType.REMOVE},
          fetch=FetchType.LAZY,orphanRemoval=true)
  private List<Movimiento> movimientos;

}
