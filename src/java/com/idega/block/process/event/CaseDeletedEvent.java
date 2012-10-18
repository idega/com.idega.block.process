package com.idega.block.process.event;

import org.springframework.context.ApplicationEvent;

public class CaseDeletedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -6549423451703385634L;

	public CaseDeletedEvent(Object source) {
		super(source);
	}

}