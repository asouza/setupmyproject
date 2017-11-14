package com.setupmyproject.controllers.forms;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.models.SpaOption;

import java.util.Collections;
import java.util.List;

public class SpaOptionForm implements CommandGenerator {

    private SpaOption option;

    @Override
    public List<? extends ProjectCommand> createComand() {
        return Collections.emptyList();
    }

    public SpaOption getOption() {
        return option;
    }

    public void setOption(SpaOption option) {
        this.option = option;
    }
}
