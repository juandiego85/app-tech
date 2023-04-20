package com.minset.apptest.service;

import com.minset.apptest.model.Cliente;

import java.util.Optional;

public interface IClienteService {
    Cliente registrar(Cliente cliente);

    Cliente modificar(Cliente cliente);

    void eliminar(Long idCliente);

    Optional<Cliente> listarId(Long idCliente);


}
