package com.idega.block.process.view;

import com.idega.block.process.business.ProcessConstants;
import com.idega.core.view.ApplicationViewNode;
import com.idega.core.view.DefaultViewNode;
import com.idega.core.view.ViewManager;
import com.idega.core.view.ViewNode;
import com.idega.idegaweb.IWBundle;
import com.idega.idegaweb.IWMainApplication;
import com.idega.repository.data.Singleton;

public class ProcessViewManager implements Singleton {
	
	/**
	 * @author Valdas Zemaitis
	 */

	private IWMainApplication iwma = null;
	
	private static final String IW_EGOV_NEST_VIEW_MANAGER_KEY = "iw_egovnestviewmanager";
	
	private ProcessViewManager(IWMainApplication iwma) {
		this.iwma = iwma;
	}
	
	public static synchronized ProcessViewManager getInstance(IWMainApplication iwma) {
		ProcessViewManager egovViewManager = (ProcessViewManager) iwma.getAttribute(IW_EGOV_NEST_VIEW_MANAGER_KEY);
	    if (egovViewManager == null) {
	    	egovViewManager = new ProcessViewManager(iwma);
	    	iwma.setAttribute(IW_EGOV_NEST_VIEW_MANAGER_KEY, egovViewManager);
	    }
	    return egovViewManager;
	}
	
	public void initializeStandardNodes(IWBundle bundle) {
		ViewNode root = ViewManager.getInstance(iwma).getWorkspaceRoot();
		DefaultViewNode casesNode = new ApplicationViewNode("cases", root);
		casesNode.setFaceletUri(bundle.getFaceletURI("cases.xhtml"));
		casesNode.setName(new StringBuilder("#{localizedStrings['").append(ProcessConstants.IW_BUNDLE_IDENTIFIER).append("']['cases']}").toString());
	}
	
}
