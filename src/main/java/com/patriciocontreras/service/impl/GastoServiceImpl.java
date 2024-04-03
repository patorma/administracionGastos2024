package com.patriciocontreras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patriciocontreras.dao.IGastoDao;
import com.patriciocontreras.dao.IGenericDao;

import com.patriciocontreras.entity.Gasto;

import com.patriciocontreras.service.IGastoService;

@Service
public class GastoServiceImpl extends CRUDImpl<Gasto, Long> implements IGastoService {

	@Autowired
	private IGastoDao gastoDao;
	
	@Override
	protected IGenericDao<Gasto, Long> getDao() {
		
		return gastoDao;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Object[]> obtenerDetallesGastos(Long idGasto) {
		
		return gastoDao.obtenerDetallesGastos(idGasto);
	}

	/*@Override
	@Transactional(readOnly = true)
	public List<TipoGasto> findAllTipos() {
		
		return gastoDao.findAllTipos();
	}*/

	@Override
	@Transactional(readOnly = true)
	public Integer subTotalGastosDetalle(Long idGasto) {
		
		return gastoDao.subTotalGastosDetalle(idGasto);
	}

	@Override
	@Transactional(readOnly = true)
	public Integer totalGastos() {
		
		return gastoDao.totalGastos();
	}

}
