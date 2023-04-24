package com.minset.apptest.controller;

import com.minset.apptest.exception.NotFoundException;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.service.IClienteService;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@Valid @RequestBody Cliente cliente) {
        Cliente clienteCreado = clienteService.registrar(cliente);
        return new ResponseEntity<>(clienteCreado, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteService.listarId(id)
                .map(clienteGuardado -> {
                    clienteGuardado.setNombre(cliente.getNombre());
                    clienteGuardado.setGenero(cliente.getGenero());
                    clienteGuardado.setEdad(cliente.getEdad());
                    clienteGuardado.setIdentificacion(cliente.getIdentificacion());
                    clienteGuardado.setDireccion(cliente.getDireccion());
                    clienteGuardado.setTelefono(cliente.getTelefono());
                    clienteGuardado.setContrasena(cliente.getContrasena());
                    clienteGuardado.setEstado(cliente.getEstado());

                    Cliente clienteActualizado = clienteService.modificar(clienteGuardado);
                    return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
                })
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProblemDetail> eliminarCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.listarId(id);
        if (!cliente.isPresent()) {
            ProblemDetail pd = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "El cliente con id " + id + " no fue encontrado en la base");
            pd.setType(URI.create("localhost:8080/clientes"));
            pd.setTitle("Cliente no encontrado");
            pd.setProperty("hostname", "localhost:8080");
            return ResponseEntity.status(404).body(pd);
        }
        clienteService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping({"/{id}"})
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable ("id")Long clienteId){
        return clienteService.listarId(clienteId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado"));
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        List<Cliente> clientes = clienteService.listar();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

}
