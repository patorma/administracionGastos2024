package com.patriciocontreras.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.entity.TipoGasto;

public interface IGastoDao extends IGenericDao<Gasto, Long> {

	@Query(value="SELECT (dg.precio_unitario) AS precio, (dg.cantidad), (dg.cantidad * dg.precio_unitario) AS SUBTOTAL FROM Tipo_Gastos t INNER JOIN Gastos g  ON (t.id = g.tipo_gasto_id) INNER JOIN Detalle_Gastos dg ON (g.id = dg.gasto_id) INNER JOIN  Pagos pa ON (g.id = pa.gasto_id) INNER JOIN Local_Pagos lp  ON (pa.local_pago_id = lp.id) WHERE g.id = ?1", nativeQuery = true)
	List<Object[]> obtenerDetallesGastos(@Param("g.id") Long idGasto);
	
	@Query("from TipoGasto")
	public List<TipoGasto> findAllTipos();
	
	@Query(value="SELECT SUM(dg.cantidad * dg.precio_unitario) AS SUBTOTAL FROM Tipo_Gastos t INNER JOIN Gastos g  ON (t.id = g.tipo_gasto_id) INNER JOIN Detalle_Gastos dg ON (g.id = dg.gasto_id) INNER JOIN  Pagos pa ON (g.id = pa.gasto_id) INNER JOIN Local_Pagos lp  ON (pa.local_pago_id = lp.id) WHERE g.id = ?1",nativeQuery = true)
	public Integer  subTotalGastosDetalle(@Param("g.id") Long idGasto);
}

