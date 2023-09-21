package com.idega.block.process.util;

import com.idega.block.process.business.ProcessConstants;
import com.idega.presentation.IWContext;
import com.idega.presentation.ListNavigator;
import com.idega.util.CoreConstants;

public class CasesPagerUtil {

	private CasesPagerUtil() {}

	public static Integer getPageSizeFromSession(IWContext iwc) {
		if (iwc == null) {
			return null;
		}

		String key = ProcessConstants.USER_CASES;
		Object pageSizeOb = iwc.getSessionAttribute(ListNavigator.PARAMETER_NUMBER_OF_ENTRIES + CoreConstants.UNDER + key);
		if (pageSizeOb instanceof Integer) {
			return (Integer) pageSizeOb;
		}
		return null;
	}

	public static Integer getPageFromSession(IWContext iwc) {
		if (iwc == null) {
			return null;
		}

		String key = ProcessConstants.USER_CASES;
		Object pageOb = iwc.getSessionAttribute(ListNavigator.PARAMETER_CURRENT_PAGE + CoreConstants.UNDER + key);
		if (pageOb instanceof Integer) {
			return (Integer) pageOb;
		}
		return null;
	}

}