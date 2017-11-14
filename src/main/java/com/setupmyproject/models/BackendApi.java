package com.setupmyproject.models;

import com.setupmyproject.commands.TimeToExecute;
import com.setupmyproject.components.ProjectCommandFormItem;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.furnace.addons.AddonRegistry;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

public enum BackendApi implements Tooltipable, ProjectCommandFormItem {


    SPRING_BOOT {
        @Override
        public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {

        }

        @Override
        public BigDecimal getPrice() {
            return BigDecimal.valueOf(5L);
        }

        @Override
        public TimeToExecute getTimeToExecute() {
            return new TimeToExecute(1, TimeUnit.HOURS);
        }
    },
    JAX_RS {
        @Override
        public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {

        }

        @Override
        public BigDecimal getPrice() {
            return BigDecimal.valueOf(5L);
        }

        @Override
        public TimeToExecute getTimeToExecute() {
            return new TimeToExecute(1, TimeUnit.HOURS);
        }
    };
}
