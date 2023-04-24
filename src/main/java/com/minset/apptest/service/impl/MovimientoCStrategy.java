package com.minset.apptest.service.impl;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.IMovimientoStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MovimientoCStrategy implements IMovimientoStrategy {
    @Override
    public void ejecutar(BigDecimal saldoAnterior, CuentaMovimientoDTO cuentaMovimientoDTO) {
        BigDecimal saldoActual = saldoAnterior.add(cuentaMovimientoDTO.getMovimiento().getValor());
        cuentaMovimientoDTO.getMovimiento().setSaldo(saldoActual);
        cuentaMovimientoDTO.getMovimiento().setCuenta(cuentaMovimientoDTO.getCuenta());
    }
}
