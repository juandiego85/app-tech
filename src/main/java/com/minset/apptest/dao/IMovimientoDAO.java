package com.minset.apptest.dao;

import com.minset.apptest.model.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IMovimientoDAO extends JpaRepository<Movimiento, Long> {

    @Query(value = "SELECT saldo\n" +
            "FROM movimiento\n" +
            "WHERE cuenta_id =:idCuenta AND movimiento_id = (\n" +
                                                            "SELECT MAX(movimiento_id) FROM movimiento \n" +
                                                            "WHERE cuenta_id =:idCuenta)", nativeQuery = true)
    BigDecimal findBalance(@Param("idCuenta") Long idCuenta);

    @Query(value = "select to_char(m.fecha, 'DD/MM/YYYY'),p.nombre,c.numero_cuenta,c.tipo_cuenta as tipo,c.saldo_inicial, c.estado,m.valor as movimiento, m.saldo as saldodisponible\n" +
            "from movimiento m, cuenta c,cliente p\n" +
            "where m.cuenta_id=c.cuenta_id\n" +
            "and p.cliente_id=c.persona_id\n" +
            "AND p.cliente_id =:clienteId\n" +
            "AND m.fecha BETWEEN :fechaInicial AND :fechaFinal\n" +
            "ORDER BY fecha DESC",nativeQuery = true)
    List<Object[]> findAllByClienteIdAndFecha(
            @Param("clienteId") Long clienteId, @Param("fechaInicial") LocalDateTime fechaInicial,
            @Param("fechaFinal") LocalDateTime fechaFinal);
}
