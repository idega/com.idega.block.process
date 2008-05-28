package com.idega.block.process.util;

import java.util.Comparator;

import com.idega.block.process.data.Case;
import com.idega.util.IWTimestamp;

public class CaseComparator implements Comparator<Case> {

	public int compare(Case case1, Case case2) {
		IWTimestamp time1 = null;
		IWTimestamp time2 = null;
		try {
			time1 = new IWTimestamp(case1.getCreated());
			time2 = new IWTimestamp(case2.getCreated());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		if (time1 == null || time2 == null) {
			return 0;
		}
		
		return time1.isEarlierThan(time2) ? 1 : 0;
	}

}
