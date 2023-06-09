package com.minset.apptest.service.impl;

import com.minset.apptest.dao.IClienteDAO;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDAO dao;
    @Override
    public Cliente registrar(Cliente cliente) {
        Optional<Cliente>clienteGuardado=dao.findByClienteId(cliente.getClienteId());
        if(clienteGuardado.isPresent()){
            throw new RuntimeException("El cliente con ese id ya existe:"+cliente.getClienteId());
        }
        return dao.save(cliente);
    }

    @Override
    public Cliente modificar(Cliente cliente) {
        dao.save(cliente);
        return cliente;
    }

    @Override
    public void eliminar(Long idPersona) {
        dao.deleteById(idPersona);
    }

    @Override
    public Optional<Cliente> listarByIdCliente(Long idCliente) {
        return dao.findByClienteId(idCliente);
    }

    @Override
    public Optional<Cliente> listarId(Long idPersona) {
        return dao.findById(idPersona);
    }

    @Override
    public List<Cliente> listar() {
        return dao.findAll();
    }
}
