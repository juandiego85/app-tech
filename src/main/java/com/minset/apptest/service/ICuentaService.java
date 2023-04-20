package com.minset.apptest.service;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Cuenta;

import java.util.Optional;

public interface ICuentaService {
    CuentaMovimientoDTO registrar(CuentaMovimientoDTO cuentaMovimientoDTO);

    Cuenta modificar(Cuenta cuenta);

    void eliminar(Long idCuenta);

    Optional<Cuenta> listarId(long idCuenta);

}
