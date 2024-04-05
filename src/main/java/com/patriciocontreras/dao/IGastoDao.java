package com.patriciocontreras.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;


public interface IGastoDao extends IGenericDao<Gasto, Long> {

	@Query(value="SELECT (dg.precio_unitario) AS precio ,(dg.cantidad) AS cantidad, (dg.cantidad * dg.precio_unitario) AS SUBTOTAL,(dg.producto) as producto,(dg.id) as id  FROM Gastos g  INNER JOIN Detalle_Gastos dg ON (g.id = dg.gasto_id) WHERE g.id = ?1 ORDER BY dg.producto ", nativeQuery = true)
	List<Object[]> obtenerDetallesGastos(@Param("g.id") Long idGasto);
	
	/*@Query("from TipoGasto")
	public List<TipoGasto> findAllTipos();
	arreglar esto
	*
	*/
	
	@Query(value="SELECT SUM(dg.cantidad * dg.precio_unitario) AS SUBTOTAL FROM Gastos g  INNER JOIN Detalle_Gastos dg ON (g.id = dg.gasto_id) WHERE g.id = ?1",nativeQuery = true)
	public Integer  subTotalGastosDetalle(@Param("g.id") Long idGasto);
	
	@Query(value="SELECT SUM(dg.cantidad * dg.precio_unitario) AS SUBTOTAL FROM Gastos g  INNER JOIN Detalle_Gastos dg ON (g.id = dg.gasto_id)",nativeQuery = true)
	public Integer totalGastos();
}

