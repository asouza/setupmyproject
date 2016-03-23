package com.setupmyproject.components;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.WebRequest;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.UserInvisible;
import com.setupmyproject.models.CommandGenerators;
import com.setupmyproject.models.ProjectDefinition;
import com.setupmyproject.models.ProjectType;
import com.setupmyproject.models.SetupState;

/**
 * Criada para ir montando o {@link SetupState} entre os requests. Estou em
 * dúvida se não podia jogar isso direto no SetupState.
 * 
 * @author alberto
 *
 */
@Component
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class OngoinSetupState {

	@Autowired
	private WebRequest request;
	private SetupState setupState;
	private CommandGenerators commandGenerators;
	
	/**
	 * para o srping
	 */
	public OngoinSetupState() {
	}
	
	/**
	 * Use quando tiver um setupstate na mão e queira restaurar o OngoingSetupState
	 * @param setupState
	 */	
	public OngoinSetupState(SetupState setupState){
		this.setupState = setupState;
		this.commandGenerators = setupState.getCommandGenerators();
	}
	
	@PostConstruct
	private void postConstruct() {
		String currentState = request.getParameter("setupState");
		if (currentState != null) {
			setupState = new SetupState(currentState);
			commandGenerators = setupState.getCommandGenerators();
		}
		request.setAttribute("ongoingSetupState", this,
				WebRequest.SCOPE_REQUEST);
		updateSetupStateInRequest();
	}

	public void updateSetupStateInRequest() {
		request.setAttribute("setupState", setupState,
				WebRequest.SCOPE_REQUEST);
	}

	public SetupState getSetupState() {
		return setupState;
	}

	public List<ProjectCommand> getCommands() {
		List<ProjectCommand> commands = commandGenerators != null ? commandGenerators
				.getCommands() : new ArrayList<ProjectCommand>();

		return filterDefaultCommands(commands);
	}

	private List<ProjectCommand> filterDefaultCommands(
			List<ProjectCommand> commands) {
		return commands
				.stream()
				.filter((command) -> {
					return !command.getClass().isAnnotationPresent(UserInvisible.class);
				}).collect(Collectors.toList());
	}

	public BigDecimal getFinalPrice() {
		return commandGenerators != null ? commandGenerators.getFinalPrice()
				: BigDecimal.ZERO;
	}
	
	public TimeToExecute getFinalTime(){
		return commandGenerators.getFinalTime();
	}

	public void updateState(SetupState state) {
		this.setupState = state;
		this.commandGenerators = state.getCommandGenerators();
		updateSetupStateInRequest();
	}
	
	/**
	 * Use para atualizar o {@link SetupState} direto pelo {@link OngoinSetupState}.
	 * @param someStepForm
	 */
	public void updateStateWithStepForm(Object someStepForm){
		this.setupState.addConf(someStepForm, (newStaate) -> {
			updateState(newStaate);
			return null;
		});
	}
	
	public ProjectDefinition getProjectType(){
		return setupState.getProjectType();
	}

}
