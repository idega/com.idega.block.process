/**
 * 
 */
package com.idega.block.process.business;

import java.util.Map;


/**
 * <p>
 * Class that holds information returned from a CaseChangeListener
 * </p>
 *  Last modified: $Date: 2006/03/06 12:47:04 $ by $Author: tryggvil $
 * 
 * @author <a href="mailto:tryggvil@idega.com">tryggvil</a>
 * @version $Revision: 1.1 $
 */
public interface CaseChangeResult {
	public String getDescription();
	public boolean wasSuccessful();
	public Map getAttributes();
}
