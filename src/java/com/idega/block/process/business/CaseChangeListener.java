/**
 * 
 */
package com.idega.block.process.business;


/**
 * <p>
 * Event Listener interfaces called by CaseBusinessBean.<br/>
 * This interface defines call-back methods that are called by the method
 * changeCaseStatus() in CaseBusiness.<br/>
 * 
 * </p>
 *  Last modified: $Date: 2006/03/06 12:47:04 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public interface CaseChangeListener {

	public CaseChangeResult beforeCaseChange(CaseChangeEvent event);
	
	public CaseChangeResult afterCaseChange(CaseChangeEvent event);
	
}
