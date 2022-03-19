package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlResourceInputStreamReaderException;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @since 1.2
 */
@Slf4j
public class YamlToObjectTransformer {

    private final FileExistenceValidator existenceValidator = new FileExistenceValidator();

    /**
     * @param source
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed object
     * @since 1.2
     */
    public <T> T getTransformedObject(FileSource source, String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        if (source == FileSource.CLASS_LOADER) {
            return getObjectFromClassLoader(yaml, filename);
        } else if (source == FileSource.FILESYSTEM) {
            return getObjectFromFilesystem(yaml, filename);
        }

        throw new YAMLException("Unknown error");
    }

    private void logError(String filename, Exception e) {
        log.error("Can't parse {}, reason: {}", filename, e.getMessage());
    }

    private <T> T getObjectFromFilesystem(Yaml yaml, String filename) {
        existenceValidator.validateFileExists(FileSource.FILESYSTEM, filename);

        try (InputStream inputStream = Files.newInputStream(Paths.get(filename), StandardOpenOption.READ)) {
            return (T) yaml.load(inputStream);
        } catch (IOException | YAMLException e) {
            logError(filename, e);

            try {
                throw e;
            } catch (IOException ex) {
                throw new YamlResourceInputStreamReaderException(e);
            }
        }
    }

    private <T> T getObjectFromClassLoader(Yaml yaml, String filename) {
        existenceValidator.validateFileExists(FileSource.CLASS_LOADER, filename);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return (T) yaml.load(inputStream);
        } catch (IOException | YAMLException e) {
            logError(filename, e);

            try {
                throw e;
            } catch (IOException ex) {
                throw new YamlResourceInputStreamReaderException(e);
            }
        }
    }

}
