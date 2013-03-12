package com.idega.block.process.business;

import com.idega.user.data.User;

/**
 * 
 * <p>Interface for getting properties from some external entities.</p>
 * <p>You can report about problems to: 
 * <a href="mailto:martynas@idega.is">Martynas Stakė</a></p>
 *
 * @version 1.0.0 Mar 7, 2013
 * @author <a href="mailto:martynas@idega.is">Martynas Stakė</a>
 */
public interface ExternalEntityInterface {
	
	/**
	 * 
	 * <p>Gets name from entity, which cannot be seen by this module</p>
	 * @param user to search entity by, not <code>null</code>;
	 * @return name of entity or <code>null</code> on failure.
	 * @author <a href="mailto:martynas@idega.com">Martynas Stakė</a>
	 */
	public String getName(User user);
}
