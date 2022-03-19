package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @since 1.2
 */
@Slf4j
public class ObjectToYamlTransformer {

    /**
     * @since 1.2
     * @param filename
     * @param instance
     */
    public void transformToFile(String filename, Object instance) {
        Yaml yaml = new Yaml();

        try (FileWriter writer = new FileWriter(filename)) {
            yaml.dump(instance, writer);
        } catch (IOException e) {
            log.error("Can't write in {}, reason: {}", filename, e.getMessage());

            throw new YamlFileNotFoundException(e);
        }
    }
}
