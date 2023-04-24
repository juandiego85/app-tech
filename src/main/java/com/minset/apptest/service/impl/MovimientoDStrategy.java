package com.minset.apptest.service.impl;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.IMovimientoStrategy;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class MovimientoDStrategy implements IMovimientoStrategy {
    @Override
    public void ejecutar(BigDecimal saldoAnterior, CuentaMovimientoDTO cuentaMovimientoDTO) {
        BigDecimal saldoActual = saldoAnterior.subtract(cuentaMovimientoDTO.getMovimiento().getValor());
        if (saldoActual.compareTo(BigDecimal.ZERO) < 0) {
            throw new RuntimeException("Saldo no disponible");
        }
        cuentaMovimientoDTO.getMovimiento().setSaldo(saldoActual);
        cuentaMovimientoDTO.getMovimiento().setCuenta(cuentaMovimientoDTO.getCuenta());
    }
}
