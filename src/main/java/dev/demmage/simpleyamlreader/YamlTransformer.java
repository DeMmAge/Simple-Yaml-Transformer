package dev.demmage.simpleyamlreader;

import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @since 1.1
 * @deprecated From 1.1 version this class was deprecated
 * use {@link YamlToObjectTransformer instead.
 */
@Slf4j
@Deprecated
public class YamlTransformer {

    private final FileExistenceValidator existenceValidator = new FileExistenceValidator();

    /**
     * @param source
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed object
     * @since 1.1
     */
    public <T> T getTransformedObject(FileSource source, String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        if (source == FileSource.CLASS_LOADER) {
            existenceValidator.validateFileExists(FileSource.CLASS_LOADER, filename);

            return getObjectFromClassLoader(yaml, filename);
        } else if (source == FileSource.FILESYSTEM) {
            existenceValidator.validateFileExists(FileSource.FILESYSTEM, filename);

            return getObjectFromFilesystem(yaml, filename);
        }

        throw new RuntimeException("Unknown error");
    }

    /**
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed class instance
     * @since 1.0
     * @deprecated From 1.1 version this method was deprecated, because use
     * {@link #getTransformedObject(FileSource source, String filename, Class<T> clazz)} instead.
     */
    @Deprecated
    public <T> T getTransformedObjectFromClassLoader(String filename, Class<T> clazz) {
        return getTransformedObject(FileSource.CLASS_LOADER, filename, clazz);
    }

    /**
     * @param filename
     * @param clazz
     * @param <T>
     * @return Transformed class instance
     * @since 1.0
     * @deprecated From 1.1 version this method was deprecated, because use
     * {@link #getTransformedObject(FileSource source, String filename, Class<T> clazz)} instead.
     */
    @Deprecated
    public <T> T getTransformedObjectFromDirectory(String filename, Class<T> clazz) {
        return getTransformedObject(FileSource.FILESYSTEM, filename, clazz);
    }

    private void logError(String filename, Exception e) {
        log.error("Can't parse {}, reason: {}", filename, e.getMessage());
    }

    private <T> T fill(Yaml yaml, String filename, InputStream stream) {
        try {
            return yaml.load(stream);
        } catch (YAMLException e) {
            logError(filename, e);
            throw e;
        }
    }

    private <T> T getObjectFromFilesystem(Yaml yaml, String filename) {
        try (InputStream stream = new FileInputStream(filename)) {
            return fill(yaml, filename, stream);
        } catch (IOException e) {
            throw new YAMLException(e);
        }
    }

    private <T> T getObjectFromClassLoader(Yaml yaml, String filename) {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return fill(yaml, filename, stream);
        } catch (IOException e) {
            throw new YAMLException(e);
        }
    }
}
