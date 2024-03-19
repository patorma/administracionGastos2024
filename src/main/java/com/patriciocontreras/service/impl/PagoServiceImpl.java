package com.patriciocontreras.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.dao.IPagoDao;
import com.patriciocontreras.entity.Gasto;
import com.patriciocontreras.entity.LocalPago;
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

	@Override
	@Transactional(readOnly = true)
	public List<Gasto> findAllGastos() {
		
		return pagoDao.findAllGastos();
	}


	@Override
	@Transactional(readOnly = true)
	public List<LocalPago> findAllLocales() {
		
		return pagoDao.findAllLocales();
	}

}
