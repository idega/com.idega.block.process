package com.idega.block.process.business.file;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.FinderException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.idega.block.process.business.CaseManagersProvider;
import com.idega.block.process.data.Case;
import com.idega.block.process.data.CaseHome;
import com.idega.business.file.FileStatisticsProvider;
import com.idega.data.IDOLookup;
import com.idega.user.data.User;
import com.idega.util.StringUtil;

@Service
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class CaseAttachmentStatisticsProvider implements FileStatisticsProvider {

	private static final long serialVersionUID = -7668215972004833935L;

	private static final Logger LOGGER = Logger.getLogger(CaseAttachmentStatisticsProvider.class.getName());
	
	@Autowired
	private CaseManagersProvider caseManagersProvider;
	
	public Collection<User> getPotentialDownloaders(String fileHolderIdentifier) {
		if (StringUtil.isEmpty(fileHolderIdentifier)) {
			return null;
		}
		
		Case theCase = null;
		try {
			CaseHome caseHome = (CaseHome) IDOLookup.getHome(Case.class);
			theCase = caseHome.findByPrimaryKey(fileHolderIdentifier);
		} catch(FinderException e) {
		} catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error getting potential attachments downloaders for case: " + fileHolderIdentifier, e);
		}
		
		return theCase == null ? null : theCase.getSubscribers();
	}

	public CaseManagersProvider getCaseManagersProvider() {
		return caseManagersProvider;
	}

	public void setCaseManagersProvider(CaseManagersProvider caseManagersProvider) {
		this.caseManagersProvider = caseManagersProvider;
	}

}
