package com.minset.apptest.controller;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.dto.MovimientosDTO;
import com.minset.apptest.exception.NotFoundException;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.ICuentaService;
import com.minset.apptest.service.IMovimientoService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Movimiento> crearMovimiento(@Valid @RequestBody CuentaMovimientoDTO cuentaMovimientoDTO) {
        if (cuentaMovimientoDTO == null || cuentaMovimientoDTO.getCuenta() == null
                || cuentaMovimientoDTO.getMovimiento() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Cuenta> optionalCuenta = cuentaService.listarid(cuentaMovimientoDTO.getCuenta().getCuentaId());
        if (optionalCuenta.isEmpty()) {
            throw new NotFoundException("Cuenta no encontrada");
        }
        Movimiento nuevoMovimiento = movimientoService.registrar(cuentaMovimientoDTO);
        if (nuevoMovimiento == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(nuevoMovimiento, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movimiento> obtenerCuentaPorId(@PathVariable Long id) {
        return movimientoService.listarId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Movimiento no encontrado"));
    }
    @GetMapping("reportes/{clienteId}")
    public List<MovimientosDTO> obtenerMovimientosPorCliente(@PathVariable Long clienteId,
                                                               @RequestParam("fechaInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaInicio,
                                                               @RequestParam("fechaFin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime fechaFin) {

        List<Object[]> resultados = movimientoService.obtenerMovimientosPorClienteYFecha(clienteId, fechaInicio, fechaFin);

        if (resultados.isEmpty()) {
            throw new NotFoundException("No existe movimientos");
        }
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