package com.minset.apptest.dto;

import com.minset.apptest.model.Cuenta;
import com.minset.apptest.model.Movimiento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CuentaMovimientoDTO {
    private Cuenta cuenta;
    private List<Movimiento> movimiento;


}
