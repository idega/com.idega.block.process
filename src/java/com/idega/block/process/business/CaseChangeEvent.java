/**
 * 
 */
package com.idega.block.process.business;

import java.util.HashMap;
import java.util.Map;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseStatus;
import com.idega.core.user.data.User;


/**
 * <p>
 * Event sent into a CaseChangeListener
 * </p>
 *  Last modified: $Date: 2006/03/06 12:47:04 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
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
