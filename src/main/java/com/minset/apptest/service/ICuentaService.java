package com.minset.apptest.service;

import com.minset.apptest.model.Cuenta;

import java.util.List;
import java.util.Optional;

public interface ICuentaService {
    Cuenta registrar(Cuenta cuenta);

    Cuenta modificar(Cuenta cuenta);

    void eliminar(Long idCuenta);

    Optional<Cuenta> listarid(long idCuenta);

    Optional<Cuenta> listarByNumeroCta(String numeroCuenta);
    List<Cuenta> listar();

}
