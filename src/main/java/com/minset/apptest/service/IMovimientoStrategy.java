package com.minset.apptest.service;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Movimiento;

import java.math.BigDecimal;

public interface IMovimientoStrategy {
    void ejecutar(BigDecimal saldoAnterior, CuentaMovimientoDTO cuentaMovimientoDTO);

}
