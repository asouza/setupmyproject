package com.setupmyproject.commands.spa;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.models.CommandGeneratorsQuery;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public class SpaStartCommand implements ProjectCommand {
    @Override
    public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {

    }

    @Override
    public BigDecimal getPrice() {
        return BigDecimal.ZERO;
    }

    @Override
    public TimeToExecute getTimeToExecute() {
        return new TimeToExecute(10, TimeUnit.MINUTES);
    }
}
