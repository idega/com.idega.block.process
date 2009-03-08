package com.idega.block.process.presentation.beans;

import java.util.Comparator;

public class CasePresentationComparator implements Comparator<CasePresentation> {

	public int compare(CasePresentation case1, CasePresentation case2) {
		return -(case1.getCreated().compareTo(case2.getCreated()));
	}

}
