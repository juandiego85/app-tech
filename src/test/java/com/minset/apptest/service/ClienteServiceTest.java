package com.minset.apptest.service;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import com.minset.apptest.dao.IClienteDAO;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest{

    private Cliente cliente;
    @BeforeEach
    void setup(){
        cliente = Cliente.builder()
                .nombre("Juan")
                .genero("M")
                .edad(30)
                .identificacion("123456789")
                .direccion("Calle 123")
                .telefono("1234567890")
                .clienteId(Long.valueOf("1"))
                .contrasena("1234")
                .estado(true)
                .build();

    }

    @Mock
    private IClienteDAO clienteDAO;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @DisplayName("Test para guardar un cliente")
    @Test
    void testGuardarCliente(){
        //given
        given(clienteDAO.findByClienteId(cliente.getClienteId()))
                .willReturn(Optional.empty());
        given(clienteDAO.save(cliente)).willReturn(cliente);

        //when
        Cliente clienteGuardado = clienteService.registrar(cliente);

        //then
        assertThat(clienteGuardado).isNotNull();

    }



}
