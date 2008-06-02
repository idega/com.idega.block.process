package com.idega.block.process.util;

import java.sql.Timestamp;
import java.util.Comparator;

import com.idega.block.process.data.Case;

public class CaseComparator implements Comparator<Case> {

	public int compare(Case case1, Case case2) {
		Timestamp time1 = case1.getCreated();
		Timestamp time2 = case2.getCreated();
		
		if (time1 == null || time2 == null) {
			return 0;
		}
		
		return time1.compareTo(time2);
	}

}
