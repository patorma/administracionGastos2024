package com.patriciocontreras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.dao.ILocalPagoDao;
import com.patriciocontreras.entity.LocalPago;
import com.patriciocontreras.service.ILocalPagoService;

@Service
public class LocalPagoServiceImpl extends CRUDImpl<LocalPago, Long> implements ILocalPagoService {

	@Autowired
	private ILocalPagoDao localPagoDao;
	
	
	@Override
	protected IGenericDao<LocalPago, Long> getDao() {
		
		return localPagoDao;
	} 

}
