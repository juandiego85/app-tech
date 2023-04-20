package com.minset.apptest.repository;
import static org.assertj.core.api.Assertions.assertThat;
import com.minset.apptest.dao.IClienteDAO;
import com.minset.apptest.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;


@DataJpaTest
public class ClienteRepositoryTest {
    @Autowired
    private IClienteDAO clienteDAO;

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

    @DisplayName("Test para registrar Cliente")
    @Test
    void testGuardarCliente(){
        // Given

        // When
        Cliente savedCliente = clienteDAO.save(cliente);

        // Then
        assertThat(savedCliente).isNotNull();
        assertThat(savedCliente.getClienteId()).isNotNull();
        assertThat(savedCliente.getNombre()).isEqualTo("Juan");
        assertThat(savedCliente.getGenero()).isEqualTo("M");
        assertThat(savedCliente.getEdad()).isEqualTo(30);
        assertThat(savedCliente.getIdentificacion()).isEqualTo("123456789");
        assertThat(savedCliente.getDireccion()).isEqualTo("Calle 123");
        assertThat(savedCliente.getTelefono()).isEqualTo("1234567890");
        assertThat(savedCliente.getClienteId()).isEqualTo(1);
        assertThat(savedCliente.getContrasena()).isEqualTo("1234");
        assertThat(savedCliente.getEstado()).isEqualTo(true);
    }

    @DisplayName("ListarClientePorId")
    @Test
    void testListarClientePorId(){
        // Given
        clienteDAO.save(cliente);

        // When
        Optional<Cliente> clienteEncontrado = clienteDAO.findByClienteId(cliente.getClienteId());

        // Then
        assertThat(clienteEncontrado).isPresent();
        Cliente cliente1 = clienteEncontrado.get();
    }

    @DisplayName("Test para actualizar un cliente")
    @Test
    void testActualizarCliente(){
        Cliente clienteActualizado = null;
        //Given
        clienteDAO.save(cliente);
        //When
        Optional<Cliente> clienteGuardadoOptional = clienteDAO.findByClienteId(cliente.getClienteId());
        if(clienteGuardadoOptional.isPresent()){
            Cliente clienteGuardado = clienteGuardadoOptional.get();
            clienteGuardado.setNombre("Jose");
            clienteGuardado.setEdad(38);
            clienteGuardado.setClienteId(Long.valueOf("2"));
            clienteActualizado = clienteDAO.save(clienteGuardado);
        }
        
        //Then
        assertThat(clienteActualizado.getClienteId()).isEqualTo(2);
        assertThat(clienteActualizado.getNombre()).isEqualTo("Jose");
        assertThat(clienteActualizado.getEdad()).isEqualTo(38);
    }

    @DisplayName("Test para eliminar un Cliente")
    @Test
    void testEliminarCliente(){
        clienteDAO.save(cliente);
        //When
        clienteDAO.deleteById(cliente.getPersonaId());
        Optional<Cliente> clienteOptional = clienteDAO.findById(cliente.getPersonaId());
        //Then
        assertThat(clienteOptional).isEmpty();

    }
}
