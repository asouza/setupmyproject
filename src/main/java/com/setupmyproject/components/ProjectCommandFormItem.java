package com.setupmyproject.components;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.hacks.MessageSourceHolder;

public interface ProjectCommandFormItem extends ProjectCommand,SpringFormListItem{
	
	@Override
	public default String getLabel() {
		return MessageSourceHolder.getPageMessages().getMessage("option."+name());
	}

}
