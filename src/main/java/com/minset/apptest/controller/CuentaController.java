package com.minset.apptest.controller;

import com.minset.apptest.exception.NotFoundException;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.service.IClienteService;
import com.minset.apptest.service.ICuentaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cuentas")
public class CuentaController {

    @Autowired
    private ICuentaService cuentaService;
    @Autowired
    private IClienteService clienteService;

    @PostMapping
    public ResponseEntity<Object> crearCuenta(@Valid @RequestBody Cuenta cuenta) {
        Optional<Cliente> optionalCliente = clienteService.listarId(cuenta.getCliente().getPersonaId());
        if (optionalCliente.isEmpty()) {
            throw new NotFoundException("Cliente no encontrado");
        }

        Optional<Cuenta> optionalCuenta = cuentaService.listarByNumeroCta(cuenta.getNumeroCuenta());
        if (!optionalCuenta.isEmpty()) {
            throw new RuntimeException("La cuenta "+cuenta.getNumeroCuenta()+" ya existe");
        }

            Cuenta nuevaCuenta = cuentaService.registrar(cuenta);
        return new ResponseEntity<>(nuevaCuenta, HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<Cuenta> obtenerCuentaPorId(@PathVariable Long id) {
        return cuentaService.listarid(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Cuenta no encontrada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cuenta> actualizarCuenta(@PathVariable Long id, @RequestBody Cuenta cuenta) {
        Optional<Cuenta> optionalCuenta = cuentaService.listarid(id);
        if (!optionalCuenta.isPresent()) {
            throw new NotFoundException("cuenta no encontrada");
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
        Optional<Cuenta> cuenta = cuentaService.listarid(id);
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cuenta>> listar() {
        List<Cuenta> cuentas = cuentaService.listar();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }
}
