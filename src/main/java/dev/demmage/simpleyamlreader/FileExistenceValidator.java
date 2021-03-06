package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FileExistenceValidator {

    public void validateFileExists(FileSource source, String filename) {
        if (source == FileSource.CLASS_LOADER) {
            validateFileExistenceInClassLoader(filename);
        } else {
            validateFileExistenceInFilesystem(filename);
        }
    }

    public void validateFileExistenceInClassLoader(String filename) {
        Optional.ofNullable(getClass().getClassLoader().getResource(filename)).orElseThrow(() -> new YamlFileNotFoundException(String.format("File %s not found", filename)));
    }

    public void validateFileExistenceInFilesystem(String filename) {
        if (!Files.exists(Paths.get(filename))) {
            throw new YamlFileNotFoundException(String.format("File %s not found", filename));
        }
    }
}
