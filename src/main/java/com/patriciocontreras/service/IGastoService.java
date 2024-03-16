package com.patriciocontreras.service;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.entity.TipoGasto;

public interface IGastoService extends ICRUD<Gasto, Long>{

	public List<Object[]> obtenerDetallesGastos(Long idGasto);
	
	public List<TipoGasto> findAllTipos();
	
	public Integer  subTotalGastosDetalle(Long idGasto);
}
