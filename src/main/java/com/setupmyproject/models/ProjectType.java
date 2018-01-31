package com.setupmyproject.models;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import com.setupmyproject.commands.spa.SpaStartCommand;
import com.setupmyproject.controllers.*;
import com.setupmyproject.wizards.*;
import org.jboss.forge.addon.facets.FacetFactory;
import org.jboss.forge.addon.javaee.cdi.CDIFacet_1_1;
import org.jboss.forge.addon.javaee.faces.FacesFacet_2_2;
import org.jboss.forge.addon.javaee.servlet.ServletFacet_3_1;
import org.jboss.forge.addon.javaee.validation.ValidationFacet;
import org.jboss.forge.addon.projects.Project;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.setupmyproject.commands.ProjectCommand;
import com.setupmyproject.commands.javaee.JSFBasicSetupCommand;
import com.setupmyproject.commands.jaxrs.JAXRSBasicSetupCommand;
import com.setupmyproject.commands.spring.SpringMVCBasicSetupCommand;
import com.setupmyproject.commands.springboot.SpringBootSetupCommand;
import com.setupmyproject.commands.vaadin.VaddinBasicSetupCommnad;
import com.setupmyproject.commands.vraptor.VRaptorBasicSetupCommnad;
import com.setupmyproject.components.SpringFormListItem;
import com.setupmyproject.controllers.vraptor.VRaptorSetupController;
import com.setupmyproject.models.crud.ChoosenFrameworkCrudConfiguration;
import com.setupmyproject.models.crud.SpringMvcCrudConfiguration;
import com.setupmyproject.wizards.vraptor.WizardVRaptorSetup;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public enum ProjectType implements SpringFormListItem, Tooltipable, ProjectDefinition {
    SPRING {
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {

            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardSpringSetup());
            steps.put(SpringSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardDBSetup());
            steps.put(DBSetupController.class, (setupState) -> new WizardCRUDSetup());
            steps.put(CrudSetupController.class, (setupState) -> new WizardPayment(setupState));
            return steps;

        }

        @Override
        public ProjectCommand getCommand() {
            return new SpringMVCBasicSetupCommand();
        }

        @Override
        public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
            return Optional.of(new SpringMvcCrudConfiguration());
        }

    },

    SPRING_BOOT {
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {

            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardSpringBootSetup());
            steps.put(SpringBootSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardDBSetup());
            steps.put(DBSetupController.class, (setupState) -> new WizardCRUDSetup());
            steps.put(CrudSetupController.class, (setupState) -> new WizardPayment(setupState));
            return steps;

        }


        @Override
        public ProjectCommand getCommand() {
            return new SpringBootSetupCommand();
        }

        @Override
        public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
            return ProjectType.SPRING.getCrudFrameworkConfiguration();
        }

    },

    JSF {
        @Override
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {
            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardDBSetup());
            steps.put(DBSetupController.class, (setupState) -> new WizardJavaEESetup());
            steps.put(JavaEESetupController.class, (setupState) -> new WizardDeltaSpike());
            steps.put(DeltaSpikeSetupController.class, (setupState) -> new WizardServerEnvironmentSetup());
            steps.put(ServerEnvironmentController.class, (setupState) -> new WizardCRUDSetup());
            steps.put(CrudSetupController.class, (setupState) -> new WizardPayment(setupState));
            return steps;
        }

        @Override
        public ProjectCommand getCommand() {
            return new JSFBasicSetupCommand();
        }

        @Override
        public void prepareFacets(FacetFactory facetFactory, Project project) {
            facetFactory.install(project, ServletFacet_3_1.class);
            facetFactory.install(project, CDIFacet_1_1.class);
            facetFactory.install(project, FacesFacet_2_2.class);
        }

        @Override
        public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
            return Optional.of(new JSFCrudConfiguration());
        }
    },
    VRAPTOR {
        @Override
        public boolean isPayed() {
            return false;
        }

        @Override
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {
            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardDBSetup());
            steps.put(DBSetupController.class, (setupState) -> new WizardVRaptorSetup());
            steps.put(VRaptorSetupController.class, (setupState) -> new WizardServerEnvironmentSetup());
            steps.put(ServerEnvironmentController.class, (setupState) -> new WizardCRUDSetup());
            steps.put(CrudSetupController.class, (setupState) -> new WizardPayment(setupState));
            return steps;
        }

        @Override
        public ProjectCommand getCommand() {
            return new VRaptorBasicSetupCommnad();
        }

        @Override
        public void prepareFacets(FacetFactory facetFactory, Project project) {
            facetFactory.install(project, ServletFacet_3_1.class);
            facetFactory.install(project, CDIFacet_1_1.class);
            facetFactory.install(project, ValidationFacet.class);
        }

        @Override
        public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
            return Optional.of(new VRaptorCrudConfiguration());
        }
    },
    VAADIN {
        @Override
        public boolean isPayed() {
            return false;
        }

        @Override
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {
            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardDBSetup());
            steps.put(DBSetupController.class, (setupState) -> new WizardPayment(setupState));
            return steps;
        }

        @Override
        public ProjectCommand getCommand() {
            return new VaddinBasicSetupCommnad();
        }
    },
    JAX_RS {
        @Override
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {
            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardJavaVersionSetup());
            steps.put(JavaSetupController.class, (setupState) -> new WizardDBSetup());
            steps.put(DBSetupController.class, (setupState) -> new WizardServerEnvironmentSetup());
            steps.put(ServerEnvironmentController.class, (setupState) -> new WizardPayment(setupState));
            return steps;
        }

        @Override
        public ProjectCommand getCommand() {
            return new JAXRSBasicSetupCommand();
        }

        @Override
        public void prepareFacets(FacetFactory facetFactory, Project project) {
        }

        @Override
        public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
            return Optional.empty();
        }
    },

    SPA {
        @Override
        public Map<Class<?>, Function<SetupState, Wizard>> steps() {
            Map<Class<?>, Function<SetupState, Wizard>> steps = new HashMap<>();

            steps.put(ProjectTypeSetupController.class, (setupState) -> new WizardMavenSetup());
            steps.put(MavenSetupController.class, (setupState) -> new WizardSpaSetup());
            steps.put(SpaOptionController.class, setupState -> new WizardBackendApiSetup());
            steps.put(BackendApiController.class, WizardPayment::new);
            return steps;
        }

        @Override
        public ProjectCommand getCommand() {
            return new SpaStartCommand();
        }

        @Override
        public void prepareFacets(FacetFactory facetFactory, Project project) {
        }

        @Override
        public Optional<ChoosenFrameworkCrudConfiguration> getCrudFrameworkConfiguration() {
            return Optional.empty();
        }
    };

}
