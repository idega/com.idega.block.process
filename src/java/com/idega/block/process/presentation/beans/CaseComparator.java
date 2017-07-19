package com.idega.block.process.presentation.beans;

import java.rmi.RemoteException;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

import javax.ejb.FinderException;

import com.idega.block.process.business.CaseBusiness;
import com.idega.block.process.data.Case;
import com.idega.business.IBOLookup;
import com.idega.business.IBOLookupException;
import com.idega.business.IBORuntimeException;
import com.idega.idegaweb.IWMainApplication;

public class CaseComparator implements Comparator<Integer> {

	private Collator collator;

	public CaseComparator() {
		super();
	}

	public CaseComparator(Locale locale) {
		this();

		if (locale != null) {
			collator = Collator.getInstance(locale);
		}
	}

	@Override
	public int compare(Integer case1Id, Integer case2Id) {
		Case case1 = null;
		Case case2 = null;
		try {
			case1 = getCaseBusiness().getCase(case1Id);
		} catch (RemoteException | FinderException e) {
		}

		try {
			case2 = getCaseBusiness().getCase(case2Id);
		} catch (RemoteException | FinderException e) {
		}

		if (case1 != null && case2 != null) {
			return collator == null ? -(case1.getCreated().compareTo(case2.getCreated())) : collator.compare(case1.getSubject(), case2.getSubject());
		} else if (case1 == null && case2 == null) {
			return 0;
		} else if (case1 == null) {
			return -1;
		} else {
			return 1;
		}
	}

	private CaseBusiness getCaseBusiness() {
		try {
			return IBOLookup.getServiceInstance(IWMainApplication.getDefaultIWApplicationContext(), CaseBusiness.class);
		}
		catch (IBOLookupException ile) {
			throw new IBORuntimeException(ile);
		}
	}

}
