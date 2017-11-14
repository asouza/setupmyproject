package com.setupmyproject.models;

import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.components.ProjectCommandFormItem;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public enum SpaOption implements Tooltipable, ProjectCommandFormItem{
    ANGULAR {
        @Override
        public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {

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


}
