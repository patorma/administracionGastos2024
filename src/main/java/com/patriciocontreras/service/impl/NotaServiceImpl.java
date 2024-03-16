package com.patriciocontreras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.dao.INotaDao;
import com.patriciocontreras.entity.Nota;
import com.patriciocontreras.service.INotaService;

@Service
public class NotaServiceImpl extends CRUDImpl<Nota, Long> implements INotaService{

	@Autowired
	private INotaDao notaDao;
	
	@Override
	protected IGenericDao<Nota, Long> getDao() {
		
		return notaDao;
	}

}
