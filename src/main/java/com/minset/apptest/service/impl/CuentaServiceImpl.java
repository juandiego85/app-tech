package com.minset.apptest.service.impl;

import com.minset.apptest.dao.ICuentaDAO;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.service.ICuentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements ICuentaService {

    @Autowired
    private ICuentaDAO dao;

    @Override
    @Transactional
    public Cuenta registrar(Cuenta cuenta) {
        cuenta.getMovimientos().forEach(x -> x.setCuenta(cuenta));
        return dao.save(cuenta);
    }

    @Override
    public Cuenta modificar(Cuenta cuenta) {
        dao.save(cuenta);
        return cuenta;
    }

    @Override
    public void eliminar(Long idCuenta) {
        dao.deleteById(idCuenta);
    }

    @Override
    public Optional<Cuenta> listarid(long idCuenta) {
        return dao.findById(idCuenta);
    }

    @Override
    public Optional<Cuenta> listarByNumeroCta(String numeroCuenta) {
        return dao.findByNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Cuenta> listar() {
        return dao.findAll();
    }

}
