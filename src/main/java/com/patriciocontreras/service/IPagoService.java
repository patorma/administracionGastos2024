package com.patriciocontreras.service;

import java.util.List;

import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.entity.LocalPago;
import com.patriciocontreras.entity.Pago;


public interface IPagoService extends ICRUD<Pago, Long> {

	public List<Gasto> findAllGastos();
	
	
	
	public List<LocalPago> findAllLocales();
}
