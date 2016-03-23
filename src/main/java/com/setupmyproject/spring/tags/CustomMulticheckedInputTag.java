package com.setupmyproject.spring.tags;

import java.lang.reflect.Method;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.tags.form.TagWriter;

import com.setupmyproject.components.PageMessages;

abstract public class CustomMulticheckedInputTag extends CustomAbstractHtmlInputElementTag {

	private static final long serialVersionUID = -6067013233825093586L;

	private String itemTitleMessageKey;

	public String getItemTitleMessageKey() {
		return itemTitleMessageKey;
	}

	public void setItemTitleMessageKey(String itemTitleMessageKey) {
		this.itemTitleMessageKey = itemTitleMessageKey;
	}

	@Override
	protected void writeOptionalAttributes(TagWriter tagWriter, Object item)
			throws JspException {
		super.writeOptionalAttributes(tagWriter, item);

		if(StringUtils.isBlank(itemTitleMessageKey)){
			return ;
		}
		
		Object inputTitleKey = null;
		try {
			Method methodToBeInvoked = item.getClass().getMethod(
					itemTitleMessageKey);
			inputTitleKey = ReflectionUtils.invokeMethod(methodToBeInvoked,
					item);
		} catch (Exception exception) {
			throw new JspException(exception);
		}

		PageMessages pageMessages = getPageMessages();
		tagWriter.writeAttribute("title",
				pageMessages.getMessage(inputTitleKey.toString(), ""));

	}

	private PageMessages getPageMessages() {
		PageMessages pageMessages = new PageMessages();
		MessageSource messageSource = RequestContextUtils
				.getWebApplicationContext(pageContext.getRequest());
		pageMessages.setMessageSource(messageSource);
		return pageMessages;
	}

}
