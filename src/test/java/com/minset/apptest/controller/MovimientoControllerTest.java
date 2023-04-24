package com.minset.apptest.controller;

import com.minset.apptest.dto.CuentaMovimientoDTO;
import com.minset.apptest.exception.NotFoundException;
import com.minset.apptest.model.Cliente;
import com.minset.apptest.model.Cuenta;
import com.minset.apptest.model.Movimiento;
import com.minset.apptest.service.ICuentaService;
import com.minset.apptest.service.IMovimientoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class MovimientoControllerTest {
    private Cliente cliente;

    @Mock
    private IMovimientoService movimientoService;
    @Mock
    private ICuentaService cuentaService;

    @InjectMocks
    private com.minset.apptest.controller.MovimientoController movimientoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("Caso de prueba 1: Se envía una cuenta y un movimiento válidos. " +
            "El método debe retornar un código de respuesta HTTP 201 (CREATED) " +
            "y el objeto Movimiento creado.")
    @Test
    public void testCrearMovimiento() {
        // Arrange
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

        Cuenta cuenta = Cuenta.builder()
                .cuentaId(1L)
                .numeroCuenta("123456")
                .tipoCuenta("Corriente")
                .saldoInicial(BigDecimal.valueOf(1000))
                .estado(true)
                .cliente(cliente)
                .build();

        Movimiento movimiento = Movimiento.builder()
                .movimientoId(1L)
                .fecha(LocalDateTime.now())
                .tipoMovimiento("C")
                .valor(BigDecimal.valueOf(100))
                .saldo(BigDecimal.valueOf(1100))
                .cuenta(cuenta)
                .build();

        CuentaMovimientoDTO cuentaMovimientoDTO = new CuentaMovimientoDTO(cuenta, movimiento);

        when(movimientoService.registrar(any(CuentaMovimientoDTO.class)))
                .thenReturn(movimiento);

        // Act
        when(cuentaService.listarid(cuenta.getCuentaId())).thenReturn(Optional.of(cuenta));
        ResponseEntity<Movimiento> response = movimientoController.crearMovimiento(cuentaMovimientoDTO);

        // Assert
        assert response.getStatusCode() == HttpStatus.CREATED;
        assert response.getBody() == movimiento;
    }


    @DisplayName("Caso de prueba 2: Se envía una cuenta y un movimiento inválidos. " +
            "El método debe retornar un código de respuesta HTTP 400 (BAD REQUEST).")
    @Test
    public void testCrearMovimientoInvalido() {
        // Arrange
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(1L);
        cuenta.setNumeroCuenta("1234567890");
        cuenta.setTipoCuenta("C");
        cuenta.setSaldoInicial(new BigDecimal("5000"));
        cuenta.setEstado(true);

        Movimiento movimiento = new Movimiento();
        movimiento.setMovimientoId(1L);
        movimiento.setFecha(LocalDateTime.now());
        movimiento.setTipoMovimiento("");
        movimiento.setValor(null);
        movimiento.setSaldo(null);
        movimiento.setCuenta(cuenta);

        CuentaMovimientoDTO cuentaMovimientoDTO = new CuentaMovimientoDTO();
        cuentaMovimientoDTO.setCuenta(cuenta);
        cuentaMovimientoDTO.setMovimiento(movimiento);

        // Act
        when(cuentaService.listarid(cuenta.getCuentaId())).thenReturn(Optional.of(cuenta));
        ResponseEntity<Movimiento> response = movimientoController.crearMovimiento(cuentaMovimientoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Caso de prueba 3: Se envía una cuenta o un movimiento nulo. " +
            "El método debe retornar un código de respuesta HTTP 400 (BAD REQUEST).")
    @Test
    public void testCrearMovimientoCuentaNulo() {
        // Arrange
        CuentaMovimientoDTO cuentaMovimientoDTO = null;

        // Act
        ResponseEntity<Movimiento> response = movimientoController.crearMovimiento(cuentaMovimientoDTO);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @DisplayName("Caso de prueba 4: Se intenta crear un movimiento para una cuenta que no existe. " +
            "El método debe retornar un código de respuesta HTTP 404 (NOT FOUND).")
    @Test
    public void testCuentaNoExiste() {
        // Arrange
        CuentaMovimientoDTO cuentaMovimientoDTO = new CuentaMovimientoDTO();
        Cuenta cuenta = new Cuenta();
        cuenta.setCuentaId(1L);
        cuentaMovimientoDTO.setCuenta(cuenta);
        Movimiento movimiento = new Movimiento();
        cuentaMovimientoDTO.setMovimiento(movimiento);

        when(cuentaService.listarid(cuenta.getCuentaId())).thenReturn(Optional.empty());

        // Act
        Exception exception = assertThrows(NotFoundException.class, () -> movimientoController.crearMovimiento(cuentaMovimientoDTO));

        // Assert
        assertEquals("Cuenta no encontrada", exception.getMessage());
    }

}
