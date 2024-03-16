package com.patriciocontreras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patriciocontreras.dao.IDetalleGastoDao;
import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.entity.DetalleGasto;
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

}
