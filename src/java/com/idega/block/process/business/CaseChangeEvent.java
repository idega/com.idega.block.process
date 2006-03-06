/*
 * $Id: CaseChangeEvent.java,v 1.2 2006/03/06 13:48:37 tryggvil Exp $
 * Created in 2006 by Tryggvi Larusson
 *
 * Copyright (C) 2006 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.util.HashMap;
import java.util.Map;
import com.idega.block.process.data.Case;
import com.idega.core.user.data.User;


/**
 * <p>
 * Event sent into a CaseChangeListener containing info about the 
 * change on the Case that is being made.
 * </p>
 *  Last modified: $Date: 2006/03/06 13:48:37 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.2 $
 */
public class CaseChangeEvent {
	private Case theCase;
	private User performer;
	private String statusFrom;
	private String statusTo;
	private Map attributes;
	
	public CaseChangeEvent(Case myCase){
		setCase(myCase);
	}

	
	/**
	 * @return Returns the attributes.
	 */
	public Map getAttributes() {
		if(attributes==null){
			attributes=new HashMap();
		}
		return attributes;
	}

	
	/**
	 * @param attributes The attributes to set.
	 */
	public void setAttributes(Map attributes) {
		this.attributes = attributes;
	}

	
	/**
	 * @return Returns the performer.
	 */
	public User getPerformer() {
		return performer;
	}

	
	/**
	 * @param performer The performer to set.
	 */
	public void setPerformer(User performer) {
		this.performer = performer;
	}

	
	/**
	 * @return Returns the statusFrom.
	 */
	public String getStatusFrom() {
		return statusFrom;
	}

	
	/**
	 * @param statusFrom The statusFrom to set.
	 */
	public void setStatusFrom(String statusFrom) {
		this.statusFrom = statusFrom;
	}

	
	/**
	 * @return Returns the statusTo.
	 */
	public String getStatusTo() {
		return statusTo;
	}

	
	/**
	 * @param statusTo The statusTo to set.
	 */
	public void setStatusTo(String statusTo) {
		this.statusTo = statusTo;
	}

	
	/**
	 * @return Returns the theCase.
	 */
	public Case getCase() {
		return theCase;
	}

	
	/**
	 * @param theCase The theCase to set.
	 */
	public void setCase(Case theCase) {
		this.theCase = theCase;
	}
	
}
