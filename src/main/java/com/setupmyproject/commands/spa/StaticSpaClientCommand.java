package com.setupmyproject.commands.spa;

import com.setupmyproject.forge.ForgeHelper;
import com.setupmyproject.infra.FilesCopyOrMoveHandleVisitor;
import com.setupmyproject.models.CommandGeneratorsQuery;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.resource.FileResource;
import org.jboss.forge.furnace.addons.AddonRegistry;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiConsumer;

public class StaticSpaClientCommand {
    private final String templatePath;

    public StaticSpaClientCommand(String path) {
        templatePath = path;
    }

    public void execute(Project project, AddonRegistry addonRegistry, CommandGeneratorsQuery commandGenerators) {

        ForgeHelper forgeHelper = new ForgeHelper(project, addonRegistry);

        Path rootProjectDirectory = getRootProjectDirectory(forgeHelper);

        makeDirectoryProjectFrom(rootProjectDirectory);

    }

    private void makeDirectoryProjectFrom(Path rootProjectDirectory) {

        try {
            makeClientApiDirectoryProjectFrom(rootProjectDirectory);
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private void makeClientApiDirectoryProjectFrom(Path rootProjectDirectory) throws IOException, URISyntaxException {
        Path clientApiPath = Files.createDirectory(rootProjectDirectory.resolve("client-api"));

        URI templatePath = getClass().getClassLoader().getResource(this.templatePath).toURI();

        FilesCopyOrMoveHandleVisitor
                .from(Paths.get(templatePath))
                    .to(clientApiPath)
                        .visitTarget()
                            .copy()
                                .walk();


    }

    private void makeBackendDirectoryProjectFrom(Path rootProjectDirectory) throws IOException {

        Path backendPath = Files.createDirectory(rootProjectDirectory.resolve("backend"));

        FilesCopyOrMoveHandleVisitor
            .from(rootProjectDirectory)
                .to(backendPath)
                    .ignoreTarget()
                        .move()
                            .walk();


    }



    private Path getRootProjectDirectory(ForgeHelper forgeHelper) {
        FileResource<?> projectDirectory = forgeHelper.getProjectDirectory();

        return Paths.get(projectDirectory.getFullyQualifiedName());
    }

}