package com.minset.apptest.controller;

import com.minset.apptest.dto.MovimientosDTO;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.ICuentaService;
import com.minset.apptest.service.IMovimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movimientos")
public class MovimientoController {

    @Autowired
    private IMovimientoService movimientoService;
    @Autowired
    private ICuentaService cuentaService;

    @PostMapping
    public ResponseEntity<Movimiento> crearMovimiento(@RequestBody Movimiento movimiento) {
        Optional<Cuenta> optionalCuenta = cuentaService.listarId(movimiento.getCuenta().getCuentaId());
        if (!optionalCuenta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cuenta cuenta = optionalCuenta.get();
        movimiento.setCuenta(cuenta);
        Movimiento nuevoMovimiento = movimientoService.registrar(movimiento);
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);

    }


    @GetMapping("/{clienteId}")
    public List<MovimientosDTO> obtenerMovimientosPorCliente(@PathVariable Long clienteId,
                                                               @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaInicio,
                                                               @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaFin) {

        List<Object[]> resultados = movimientoService.obtenerMovimientosPorClienteYFecha(clienteId, fechaInicio, fechaFin);
        List<MovimientosDTO> movimientosDTO = new ArrayList<>();

        for (Object[] resultado : resultados) {
            MovimientosDTO movimientoDTO = new MovimientosDTO();
            movimientoDTO.setFecha((String) resultado[0]);
            movimientoDTO.setNombreCliente((String) resultado[1]);
            movimientoDTO.setNumeroCuenta((String) resultado[2]);
            movimientoDTO.setTipo((String) resultado[3]);
            movimientoDTO.setSaldoInicial((Double) resultado[4]);
            movimientoDTO.setEstado((Boolean) resultado[5]);
            movimientoDTO.setValorMovimiento((Double) resultado[6]);
            movimientoDTO.setSaldoDisponible((Double) resultado[7]);
            movimientosDTO.add(movimientoDTO);
        }

        return movimientosDTO;
    }


}