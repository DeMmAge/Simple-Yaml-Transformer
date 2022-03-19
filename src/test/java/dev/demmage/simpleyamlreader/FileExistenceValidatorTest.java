package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FileExistenceValidatorTest {

    private static FileExistenceValidator validator;

    @BeforeAll
    static void init() {
        validator = new FileExistenceValidator();
    }

    @Test
    void shouldThrowExceptionThenFileNotExistsInClassloader() {
        assertThrows(YamlFileNotFoundException.class, () ->
                validator.validateFileExists(FileSource.CLASS_LOADER, "not_exists.yml"));
    }

    @Test
    void shouldThrowExceptionThenFileNotExistsInFilesystem() {
        assertThrows(YamlFileNotFoundException.class, () ->
                validator.validateFileExists(FileSource.FILESYSTEM, "not_exists.yml"));
    }

    @Test
    void shouldThenFileExistsInFilesystem() {
        validator.validateFileExists(FileSource.FILESYSTEM, "external/test/resources/ymls/valid.yml");
    }

    @Test
    void shouldThenFileExistsInClassloader() {
        validator.validateFileExists(FileSource.CLASS_LOADER, "valid.yml");
    }
}