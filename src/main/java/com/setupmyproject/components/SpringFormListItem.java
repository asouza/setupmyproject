package com.setupmyproject.components;

import com.setupmyproject.hacks.MessageSourceHolder;

/**
 * Alguns elementos participam de checkboxes,radios,selects etc. E precisa ter
 * uma label para o Spring mostrar. Vou fazer o povo implementar a interface.
 * 
 * @author alberto
 *
 */
public interface SpringFormListItem {

	public default String getLabel() {
		return MessageSourceHolder.getPageMessages().getMessage(name());
	}

	/*
	 * hack para ser usado com a enum
	 */
	public String name();
}
