package com.setupmyproject.controllers.forms;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.jaxrs.JAXRSBasicSetupCommand;
import com.setupmyproject.models.BackendApi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BackendApiForm implements CommandGenerator {

    private BackendApi option;

    @Override
    public List<? extends ProjectCommand> createComand() {
        return Arrays.asList(new JAXRSBasicSetupCommand());
    }

    public BackendApi getOption() {
        return option;
    }

    public void setOption(BackendApi option) {
        this.option = option;
    }
}
