package com.idega.block.process.event;

import org.springframework.context.ApplicationEvent;

public class CaseModifiedEvent extends ApplicationEvent {

	private static final long serialVersionUID = -8070920530749497099L;
	
	public CaseModifiedEvent(Object source) {
		super(source);
	}
}