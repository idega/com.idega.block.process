package com.idega.block.process.presentation.beans;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class CasePresentationComparator implements Comparator<CasePresentation> {

	private Collator collator;
	
	public CasePresentationComparator() {
		super();
	}
	
	public CasePresentationComparator(Locale locale) {
		this();
		
		if (locale != null) {
			collator = Collator.getInstance(locale);
		}
	}
	
	public int compare(CasePresentation case1, CasePresentation case2) {
		return collator == null ? -(case1.getCreated().compareTo(case2.getCreated())) : collator.compare(case1.getSubject(), case2.getSubject());
	}

}
