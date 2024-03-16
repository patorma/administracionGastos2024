package com.patriciocontreras.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.patriciocontreras.dao.IGenericDao;
import com.patriciocontreras.dao.ITipoGastoDao;
import com.patriciocontreras.entity.TipoGasto;
import com.patriciocontreras.service.ITipoGastoService;

@Service
public class TipoGastoServiceImpl extends CRUDImpl<TipoGasto, Long> implements ITipoGastoService  {

    @Autowired
    private ITipoGastoDao tipoGastoDao;
	
	@Override
	protected IGenericDao<TipoGasto, Long> getDao() {
		
		return tipoGastoDao;
	}

}
