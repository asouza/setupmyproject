package com.setupmyproject.hacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.util.ReflectionUtils;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.components.PriceDisplayer;
import com.setupmyproject.components.SpringFormListItem;

/**
 * Ta no hack por conta desse reflection esperto para chamar o getCommand()
 * 
 * @author alberto
 *
 */
public class SpringFormListItemDisplayerHelper {

	/**
	 * 
	 * @param object
	 *            algum objeto que possua o método ProjectCommand getCommand()
	 * @return nome do comando com preço associado. Ex:JSF 2.2(USD5.00)
	 */
	public static String showWithPrice(SpringFormListItem object) {
		return show(object, object.name());
	}
	
	/**
	 * 
	 * @param object
	 *            algum objeto que possua o método ProjectCommand getCommand()
	 * @return
	 */
	public static String show(SpringFormListItem object, String messageKey) {
		Method getCommand = ReflectionUtils.findMethod(object.getClass(),
				"getCommand");
		ProjectCommand command;
		if (getCommand != null) {
			try {
				getCommand.setAccessible(true);
				command = (ProjectCommand) getCommand.invoke(object);
			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				throw new RuntimeException(e);
			}
		} else {
			if(object instanceof ProjectCommand){
				command = (ProjectCommand)object;
			} else {
				throw new IllegalArgumentException("O objeto passado como parametro deve ter o método getCommand ou ser um ProjectCommand."+object.getClass());
			}
		}

		return new PriceDisplayer().show(command.getPrice(),
				MessageSourceHolder.getPageMessages().getMessage(messageKey));
	}
}
