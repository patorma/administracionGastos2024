package com.patriciocontreras.service;

import java.util.List;

import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;

public interface IDetalleGastoService extends ICRUD<DetalleGasto, Long>{

	public List<Gasto> findAllGastos();
}
