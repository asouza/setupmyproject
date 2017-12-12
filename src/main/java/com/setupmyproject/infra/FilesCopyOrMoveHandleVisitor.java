package com.setupmyproject.infra;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.function.BiConsumer;

public class FilesCopyOrMoveHandleVisitor extends SimpleFileVisitor<Path> {


    private final Path source;
    private final Path target;
    private final boolean ignoreTarget;
    private final BiConsumer<Path, Path> consumer;

    private FilesCopyOrMoveHandleVisitor(Path source, Path target, boolean ignoreTarget, BiConsumer<Path, Path> consumer){
        this.source = source;
        this.target = target;
        this.ignoreTarget = ignoreTarget;
        this.consumer = consumer;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        System.out.println(file);
        if (file.equals(target) && ignoreTarget){
            return FileVisitResult.SKIP_SUBTREE;
        }


        action(file);

        return FileVisitResult.CONTINUE;
    }

    private void action(Path file) throws IOException {
        Path destination = target.resolve(source.relativize(file));
        consumer.accept(file, destination);
    }

    public static TargetPart from(Path source){
        return new TargetPart(source);
    }


    static public class Builder {

        private final boolean ignoreTarget;
        private final Path target;
        private final Path source;
        private final BiConsumer<Path, Path> consumer;

        public Builder(Path source, Path target, boolean ignoreTarget, BiConsumer<Path, Path> consumer) {
            this.ignoreTarget = ignoreTarget;
            this.target = target;
            this.source = source;
            this.consumer = consumer;
        }

        public void walk(){
            try {
                Files.walkFileTree(source, new FilesCopyOrMoveHandleVisitor(source, target, ignoreTarget, consumer) );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static public class TargetPart {

        private final Path source;

        TargetPart(Path source) {
            this.source = source;
        }

        public IgnoreTargetPart to(Path target){
            return new IgnoreTargetPart(source, target);
        }

    }


    static public class IgnoreTargetPart{

        private final Path source;
        private final Path target;

        IgnoreTargetPart(Path source, Path target) {
            this.source = source;
            this.target = target;
        }


        public ConsumerPart ignoreTarget(){
            return new ConsumerPart(source, target, true);
        }

        public ConsumerPart visitTarget(){
            return new ConsumerPart(source, target, false);
        }

    }

    static public class ConsumerPart {

        private final Path source;
        private final Path target;
        private final boolean ignoreTarget;

        ConsumerPart(Path source, Path target, boolean ignoreTarget) {
            this.source = source;
            this.target = target;
            this.ignoreTarget = ignoreTarget;
        }

        public Builder copy(){
            return new Builder(source, target, ignoreTarget, this::copy);
        }

        public Builder move(){
            return new Builder(source, target, ignoreTarget, this::move);
        }


        private void move(Path source, Path target){
            try {
                Files.move(source,target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private void copy(Path source, Path target){
            try {
                Path dir = target.getParent();

                if (Files.notExists(dir)) {
                    Files.createDirectories(dir);
                }

                Files.copy(source, target);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}