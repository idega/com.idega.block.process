/*
 * $Id: CaseChangeEvent.java,v 1.3 2006/04/09 11:42:34 laddi Exp $
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
 *  Last modified: $Date: 2006/04/09 11:42:34 $ by $Author: laddi $
 * 
 * @author <a href="mailto:tryggvil@idega.com">Tryggvi Larusson</a>
 * @version $Revision: 1.3 $
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
		if(this.attributes==null){
			this.attributes=new HashMap();
		}
		return this.attributes;
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
		return this.performer;
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
		return this.statusFrom;
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
		return this.statusTo;
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
		return this.theCase;
	}

	
	/**
	 * @param theCase The theCase to set.
	 */
	public void setCase(Case theCase) {
		this.theCase = theCase;
	}
	
}
