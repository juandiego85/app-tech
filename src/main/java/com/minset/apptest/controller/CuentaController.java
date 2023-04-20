package com.minset.apptest.controller;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.service.IClienteService;
import com.minset.apptest.service.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaService cuentaService;

    @Autowired
    private IClienteService clienteService;


    @PostMapping
    public ResponseEntity<CuentaMovimientoDTO> crearCuentasss(@RequestBody CuentaMovimientoDTO cuentaMovimientoDTO) {
        Optional<Cliente> optionalCliente = clienteService.listarId(cuentaMovimientoDTO.getCuenta().getCliente().getClienteId());
        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = optionalCliente.get();
        cuentaMovimientoDTO.getCuenta().setCliente(cliente);

        CuentaMovimientoDTO nuevaCuenta = cuentaService.registrar(cuentaMovimientoDTO);

        return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        return cuentaService.listarId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Optional<Cuenta> optionalCuenta = cuentaService.listarId(id);
        if (!optionalCuenta.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Cuenta cuentaActualizada = optionalCuenta.get();
        cuentaActualizada.setNumeroCuenta(cuenta.getNumeroCuenta());
        cuentaActualizada.setTipoCuenta(cuenta.getTipoCuenta());
        cuentaActualizada.setSaldoInicial(cuenta.getSaldoInicial());
        cuentaActualizada.setEstado(cuenta.getEstado());

        Cuenta cuentaGuardada = cuentaService.modificar(cuentaActualizada);

        return new ResponseEntity<>(cuentaGuardada, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProblemDetail> eliminarCuenta(@PathVariable Long id) {
        Optional<Cuenta> cuenta = cuentaService.listarId(id);
        if (!cuenta.isPresent()) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "La cuenta con id " + id + " no fue encontrado en la base");
            pd.setType(URI.create("localhost:8080/cuentas"));
            pd.setTitle("cuenta no encontrado");
            pd.setProperty("hostname", "localhost:8080");
            return ResponseEntity.status(404).body(pd);
        }
        cuentaService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
