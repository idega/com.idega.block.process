package com.idega.block.process.presentation;

import javax.faces.webapp.UIComponentTag;

public class CasesListViewerTag extends UIComponentTag {

	@Override
	public String getComponentType() {
		return "CasesListViewer";
	}

	@Override
	public String getRendererType() {
		return null;
	}

}
