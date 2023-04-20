package com.minset.apptest.service.impl;

import com.minset.apptest.dao.IMovimientoDAO;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.IMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    @Autowired
    private IMovimientoDAO dao;
    @Override
    public Movimiento registrar(Movimiento movimiento) {
        Double saldoAnterior = dao.findBalance(movimiento.getCuenta().getCuentaId());
        String tipo = movimiento.getTipoMovimiento();
        Double saldoActual = 0.0;
        if (tipo.equals("C")){
            saldoActual=saldoAnterior+movimiento.getValor();
            movimiento.setSaldo(saldoActual);
            dao.save(movimiento);
        }else if(tipo.equals("D")){
            if(saldoAnterior >= movimiento.getValor()){
                saldoActual=saldoAnterior- movimiento.getValor();
                movimiento.setSaldo(saldoActual);
                dao.save(movimiento);
            }else{
                throw new RuntimeException("Saldo no disponible");
            }
       }
        return movimiento;
    }

    @Override
    public Optional<Movimiento> listarId(Long idMovimiento) {
        return dao.findById(idMovimiento);
    }

    @Override
    public List<Object[]> obtenerMovimientosPorClienteYFecha(Long clienteId, LocalDateTime fechaInicial,
                                                                   LocalDateTime fechaFinal) {
        return  dao.findAllByClienteIdAndFecha(clienteId, fechaInicial, fechaFinal);
    }
}
