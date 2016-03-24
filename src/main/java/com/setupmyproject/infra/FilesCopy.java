package com.setupmyproject.infra;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FilesCopy extends SimpleFileVisitor<Path>{

	private Path source;
	private Path destiny;

	public FilesCopy(Path origem, Path destino) {
		this.source = origem;
		this.destiny = destino;
	}

	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		copyPath(dir);
		return FileVisitResult.CONTINUE;
	}

	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		copyPath(file);
		return FileVisitResult.CONTINUE;
	}

	private void copyPath(Path entry) throws IOException {
		Path novoDiretorio = destiny.resolve(source.relativize(entry));
		Files.copy(entry, novoDiretorio);
	}
	
}
