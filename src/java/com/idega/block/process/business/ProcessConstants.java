/*
 * $Id: ProcessConstants.java,v 1.2 2009/03/09 15:59:24 valdas Exp $
 * Created on Sep 24, 2005
 *
 * Copyright (C) 2005 Idega Software hf. All Rights Reserved.
 *
 * This software is the proprietary information of Idega hf.
 * Use is subject to license terms.
 */
package com.idega.block.process.business;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.idega.idegaweb.IWResourceBundle;
import com.idega.presentation.IWContext;
import com.idega.presentation.text.Text;
import com.idega.repository.data.Singleton;
import com.idega.user.data.User;

/**
 * Last modified: $Date: 2009/03/09 15:59:24 $ by $Author: valdas $
 *
 * @author <a href="mailto:laddi@idega.com">laddi</a>
 * @version $Revision: 1.2 $
 */
public final class ProcessConstants implements Singleton {

	public static final String	CASE_IDENTIFIER = "string_caseIdentifier",
								CASE_DESCRIPTION = "string_caseDescription",
								FINANCING_OF_THE_TASKS = "objlist_ownerFinanceOfTasks",
								BOARD_FINANCING_SUGGESTION = "string_ownerGradeComment",
								BOARD_FINANCING_DECISION = "string_ownerGrantAmauntValue";

	private ProcessConstants() {}

	public static final String	IW_BUNDLE_IDENTIFIER = "com.idega.block.process",
								EGOV_NEST_CASES_CACHE_KEY = "egovNestCasesCacheKey",
								ACTIVE_PROCESS_DEFINITION = "active_process_definition_id",
								METADATA_KEY_URL="CASE_URL",
								CASE_LIST_TYPE_SEARCH_RESULTS = "searchResults",
								HANDLER_IDENTIFIER = "handlerUserId";

	public static final List<CasesListColumn> getCasesListMainColumns(IWResourceBundle iwrb) {
		CasesListColumn[] columns = new CasesListColumn[6];
		Random generator = new Random();

		columns[0] = new CasesListColumn(iwrb.getLocalizedString("number", "Number"), "number", String.valueOf(generator.nextInt(Integer.MAX_VALUE)));
		columns[1] = new CasesListColumn(iwrb.getLocalizedString("status", "Status"), "string", String.valueOf(generator.nextInt(Integer.MAX_VALUE)));
		columns[2] = new CasesListColumn(Text.NON_BREAKING_SPACE, null, String.valueOf(generator.nextInt(Integer.MAX_VALUE)));
		columns[3] = new CasesListColumn(iwrb.getLocalizedString("sender", "Sender"), "string", String.valueOf(generator.nextInt(Integer.MAX_VALUE)));
		columns[4] = new CasesListColumn(iwrb.getLocalizedString("handler", "Handler"), "string", String.valueOf(generator.nextInt(Integer.MAX_VALUE)));
		columns[5] = new CasesListColumn(iwrb.getLocalizedString("date", "Date"), "date", String.valueOf(generator.nextInt(Integer.MAX_VALUE)));

		return Collections.unmodifiableList(Arrays.asList(columns));
	}

	public static final String getKeyForCasesColumnsAttribute(IWContext iwc) {
		if (iwc == null) {
			throw new NullPointerException(ProcessConstants.class.getName() + ": IWContext is null");
		}
		User user = iwc.getCurrentUser();
		if (user == null) {
			throw new NullPointerException(ProcessConstants.class.getName() + ": IWContext is null");
		}
		return new StringBuilder(iwc.getSession().getId()).append(user.getId()).toString();
	}

}