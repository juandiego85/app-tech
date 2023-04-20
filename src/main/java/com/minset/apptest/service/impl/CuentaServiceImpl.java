package com.minset.apptest.service.impl;

import com.minset.apptest.dao.ICuentaDAO;
import com.minset.apptest.dao.IMovimientoDAO;
import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.service.ICuentaService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CuentaServiceImpl implements ICuentaService {

    @Autowired
    private ICuentaDAO dao;
    @Autowired
    private IMovimientoDAO daoMovimiento;

    @Override
    @Transactional
    public CuentaMovimientoDTO registrar(CuentaMovimientoDTO cuentaMovimientoDTO) {
        cuentaMovimientoDTO.getCuenta().getMovimientos().forEach(x -> x.setCuenta(cuentaMovimientoDTO.getCuenta()));
        dao.save(cuentaMovimientoDTO.getCuenta());
        return cuentaMovimientoDTO;

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
    public Optional<Cuenta> listarId(long idCuenta) {
        return dao.findById(idCuenta);
    }

}
