package com.minset.apptest.service;

import com.minset.apptest.model.Movimiento;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface IMovimientoService {
    Movimiento registrar(Movimiento movimiento);

    Optional<Movimiento> listarId(Long idMovimiento);

    List<Object[]> obtenerMovimientosPorClienteYFecha(Long clienteId, LocalDateTime fechaInicial,
                                           LocalDateTime fechaFinal);



}
