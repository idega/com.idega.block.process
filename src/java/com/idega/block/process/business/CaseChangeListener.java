/*
 * $Id: CaseChangeListener.java,v 1.2 2006/03/06 13:45:43 tryggvil Exp $
 * Created in 2006 by Tryggvi Larusson
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;


/**
 * <p>
 * Event Listener interfaces called by CaseBusinessBean.<br/>
 * This interface defines call-back methods that are called by the method
 * changeCaseStatus() in CaseBusiness.<br/>
 * 
 * </p>
 *  Last modified: $Date: 2006/03/06 13:45:43 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.2 $
 */
public interface CaseChangeListener {

	/**
	 * <p>
	 * Called before the status change is stored on the case
	 * by changeCaseStatus in CaseBusinessBean
	 * </p>
	 * @param event event containing the Case and other info that is about
	 * to be changed.
	 * @return
	 */
	public CaseChangeResult beforeCaseChange(CaseChangeEvent event);

	/**
	 * <p>
	 * Called after the status change is stored on the case
	 * by changeCaseStatus in CaseBusinessBean
	 * </p>
	 * @param event event containing the Case and other info that has
	 * been changed.
	 * @return
	 */
	public CaseChangeResult afterCaseChange(CaseChangeEvent event);
	
}
