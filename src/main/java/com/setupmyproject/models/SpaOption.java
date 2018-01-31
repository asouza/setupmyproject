package com.setupmyproject.models;

import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.commands.spa.StaticSpaClientCommand;
import com.setupmyproject.components.ProjectCommandFormItem;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

public enum SpaOption implements Tooltipable, ProjectCommandFormItem{
    ANGULAR {
        @Override
        public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {

            new StaticSpaClientCommand("templates/spa/client/client-angular-api").execute(project, addonRegistry, commandGenerators);

        }

        @Override
        public BigDecimal getPrice() {
            return BigDecimal.ONE;
        }

        @Override
        public TimeToExecute getTimeToExecute() {
            return new TimeToExecute(10, TimeUnit.MINUTES);
        }
    },
    REACTJS {
        @Override
        public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {
            new StaticSpaClientCommand("templates/spa/client/client-react-api").execute(project, addonRegistry, commandGenerators);
        }

        @Override
        public BigDecimal getPrice() {
            return BigDecimal.ONE;
        }

        @Override
        public TimeToExecute getTimeToExecute() {
            return new TimeToExecute(10, TimeUnit.MINUTES);
        }
    };

    @Override
    public String getNameKey() {
        return "option." + name();
    }
}
