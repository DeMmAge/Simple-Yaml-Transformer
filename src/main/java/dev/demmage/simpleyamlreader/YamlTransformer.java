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

@Slf4j
public class YamlTransformer {

    private final FileExistenceValidator existenceValidator = new FileExistenceValidator();

    /**
     * @since 1.1
     * @param source
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed object
     */
    public <T> T getTransformedObject(FileSource source, String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        if (source == FileSource.CLASS_LOADER) {
            return getObjectFromClassLoader(yaml, filename);
        } else if (source == FileSource.FILESYSTEM) {
            return getObjectFromFilesystem(yaml, filename);
        }

        throw new RuntimeException("Unknown error");
    }

    /**
     * @since 1.0
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed class instance
     * @deprecated From 1.1 version this method was deprecated, because use
     * {@link #getTransformedObject(FileSource source, String filename, Class<T> clazz)} instead.
     */
    @Deprecated
    public <T> T getTransformedObjectFromClassLoader(String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        existenceValidator.validateFileExists(FileSource.CLASS_LOADER, filename);

        return getObjectFromClassLoader(yaml, filename);
    }

    /**
     * @since 1.0
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed class instance
     * @deprecated From 1.1 version this method was deprecated, because use
     * {@link #getTransformedObject(FileSource source, String filename, Class<T> clazz)} instead.
     */
    @Deprecated
    public <T> T getTransformedObjectFromDirectory(String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        existenceValidator.validateFileExists(FileSource.FILESYSTEM, filename);

        return getObjectFromFilesystem(yaml, filename);
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
