/**
 * 
 */
package com.idega.block.process.business;



import com.idega.business.IBOHomeImpl;

/**
 * @author bluebottle
 *
 */
public class CaseBusinessHomeImpl extends IBOHomeImpl implements
		CaseBusinessHome {
	protected Class getBeanInterfaceClass() {
		return CaseBusiness.class;
	}

	public CaseBusiness create() throws javax.ejb.CreateException {
		return (CaseBusiness) super.createIBO();
	}

}
