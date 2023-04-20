package com.minset.apptest.dao;

import com.minset.apptest.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IClienteDAO extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByClienteId(Long clienteId);
}
