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

    public <T> T getTransformedObjectFromClassLoader(String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        existenceValidator.validateFileExistenceInClassLoader(filename);

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filename)) {
            return (T) yaml.load(inputStream);
        } catch (IOException | YAMLException e) {
            log.error("Can't parse {}, reason: {}", filename, e.getMessage());

            try {
                throw e;
            } catch (IOException ex) {
                throw new YamlResourceInputStreamReaderException(e);
            }
        }
    }

    public <T> T getTransformedObjectFromDirectory(String filename, Class<T> clazz) {
        Yaml yaml = new Yaml(new Constructor(clazz));

        existenceValidator.validateFileExistenceInFilesystem(filename);

        try (InputStream inputStream = Files.newInputStream(Paths.get(filename), StandardOpenOption.READ)) {
            return (T) yaml.load(inputStream);
        } catch (IOException | YAMLException e) {
            log.error("Can't parse {}, reason: {}", filename, e.getMessage());

            try {
                throw e;
            } catch (IOException ex) {
                throw new YamlResourceInputStreamReaderException(e);
            }
        }
    }
}
