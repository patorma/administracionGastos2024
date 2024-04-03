package com.patriciocontreras.service;

import java.util.List;


import com.patriciocontreras.entity.Gasto;


public interface IGastoService extends ICRUD<Gasto, Long>{

	public List<Object[]> obtenerDetallesGastos(Long idGasto);
	
	//public List<TipoGasto> findAllTipos();
	
	public Integer  subTotalGastosDetalle(Long idGasto);
	
	public Integer totalGastos();
}
