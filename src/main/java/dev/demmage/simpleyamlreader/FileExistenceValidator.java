package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class FileExistenceValidator {

    public void validateFileExistenceInClassLoader(String filename) {
        Optional.ofNullable(getClass().getClassLoader().getResource(filename)).orElseThrow(() -> new YamlFileNotFoundException(String.format("File %s not found", filename)));
    }

    public void validateFileExistenceInFilesystem(String filename) {
        boolean exists = Files.exists(Paths.get(filename));

        if (!exists) {
            throw new YamlFileNotFoundException(String.format("File %s not found", filename));
        }
    }
}
