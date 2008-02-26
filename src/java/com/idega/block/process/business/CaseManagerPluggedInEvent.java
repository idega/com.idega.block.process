package com.idega.block.process.business;


import org.springframework.context.ApplicationEvent;


/**
 * @author <a href="mailto:civilis@idega.com">Vytautas Čivilis</a>
 * @version $Revision: 1.1 $
 *
 * Last modified: $Date: 2008/02/26 19:28:36 $ by $Author: civilis $
 */
public class CaseManagerPluggedInEvent extends ApplicationEvent {

	private static final long serialVersionUID = 2033689951503691347L;
	private CaseManager caseHandler;

	public CaseManagerPluggedInEvent(Object source) {
        super(source);
        
        caseHandler = (CaseManager)source;
    }

	public CaseManager getCaseHandler() {
		return caseHandler;
	}
}