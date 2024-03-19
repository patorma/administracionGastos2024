package com.patriciocontreras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patriciocontreras.dao.IDetalleGastoDao;
import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.entity.DetalleGasto;
import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.service.IDetalleGastoService;

@Service
public class DettalleGastoServiceImpl extends CRUDImpl<DetalleGasto, Long> implements IDetalleGastoService {

	@Autowired
	private IDetalleGastoDao detalleGastoDao;
	
	
	@Override
	protected IGenericDao<DetalleGasto, Long> getDao() {
	
	    //detalleGastoDao.
		return detalleGastoDao;
	}


	@Override
	@Transactional(readOnly = true)
	public List<Gasto> findAllGastos() {
		
		return detalleGastoDao.findAllGastos();
	}

}
