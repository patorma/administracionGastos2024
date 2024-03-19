package com.patriciocontreras.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;

public interface IDetalleGastoDao extends IGenericDao<DetalleGasto, Long> {
	
	@Query("from Gasto")
	public List<Gasto> findAllGastos();
   // int calcularSubtotal(int cantidad, double precio);
}
