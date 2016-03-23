package com.setupmyproject.models;

import org.springframework.web.servlet.ModelAndView;

import com.setupmyproject.components.SpringFormListItem;

/**
 * Representa os tipos possíveis de setups. 
 * @author alberto
 *
 */
public enum SetupType implements Tooltipable,SpringFormListItem{

	NEW_PROJECT {
		@Override
		public ModelAndView getNextPage() {
			return new ModelAndView("redirect:/setup");
		}
	},CRUD_FOR_EXISTING_PROJECT {
		@Override
		public ModelAndView getNextPage() {
			return new ModelAndView("redirect:/existing/setup");
		}
	};

	abstract public ModelAndView getNextPage();
}
