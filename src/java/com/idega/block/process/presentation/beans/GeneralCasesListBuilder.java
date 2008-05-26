package com.idega.block.process.presentation.beans;

import javax.faces.component.UIComponent;

import com.idega.block.process.data.Case;
import com.idega.presentation.IWContext;
import com.idega.presentation.PresentationObject;
import com.idega.presentation.text.Link;

public interface GeneralCasesListBuilder {

	public static final String SPRING_BEAN_IDENTIFIER = "GeneralCasesListBuilder";
	
	public UIComponent getCasesList(IWContext iwc, String casesProcessorType, String prefix, boolean showCheckBoxes);
	
	public Link getProcessLink(PresentationObject object, Case theCase);
	
}
