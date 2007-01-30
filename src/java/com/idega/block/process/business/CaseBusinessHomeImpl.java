package com.idega.block.process.business;


import javax.ejb.CreateException;

import com.idega.business.IBOHomeImpl;

public class CaseBusinessHomeImpl extends IBOHomeImpl implements CaseBusinessHome {
	public Class getBeanInterfaceClass() {
		return CaseBusiness.class;
	}

	public CaseBusiness create() throws CreateException {
		return (CaseBusiness) super.createIBO();
	}
}