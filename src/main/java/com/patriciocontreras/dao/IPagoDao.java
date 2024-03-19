package com.patriciocontreras.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.entity.LocalPago;
import com.patriciocontreras.entity.Pago;


public interface IPagoDao extends IGenericDao<Pago, Long> {

	@Query("from Gasto")
	public List<Gasto> findAllGastos();
	

	
	@Query("from LocalPago")
	public List<LocalPago> findAllLocales();
}
