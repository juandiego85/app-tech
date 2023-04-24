package com.minset.apptest.service.impl;

import com.minset.apptest.dao.IMovimientoDAO;
import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.IMovimientoService;
import com.minset.apptest.service.IMovimientoStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements IMovimientoService {

    @Autowired
    private IMovimientoDAO dao;
    private Map<String, IMovimientoStrategy> estrategias = new HashMap<>();

    public  MovimientoServiceImpl(MovimientoCStrategy movimientoCStrategy, MovimientoDStrategy movimientoDStrategy) {
        estrategias.put("C", movimientoCStrategy);
        estrategias.put("D", movimientoDStrategy);
    }

    @Override
    public Movimiento registrar(CuentaMovimientoDTO movimientoDTO) {
        BigDecimal saldoAnterior = dao.findBalance(movimientoDTO.getCuenta().getCuentaId());
        IMovimientoStrategy estrategia = estrategias.get(movimientoDTO.getMovimiento().getTipoMovimiento());
        estrategia.ejecutar(saldoAnterior, movimientoDTO);
        dao.save(movimientoDTO.getMovimiento());
        return movimientoDTO.getMovimiento();
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
