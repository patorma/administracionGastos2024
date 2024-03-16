package com.patriciocontreras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.dao.IPagoDao;
import com.patriciocontreras.entity.Pago;
import com.patriciocontreras.service.IPagoService;

@Service
public class PagoServiceImpl extends CRUDImpl<Pago, Long> implements IPagoService {

	@Autowired
	private IPagoDao pagoDao;
	
	@Override
	protected IGenericDao<Pago, Long> getDao() {
	
		return pagoDao;
	}

}
