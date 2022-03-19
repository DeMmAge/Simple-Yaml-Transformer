package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class YamlToObjectTransformerTest {

    private static YamlToObjectTransformer yamlToObjectTransformer;

    @BeforeAll
    static void init() {
        yamlToObjectTransformer = new YamlToObjectTransformer();
    }

    @Test
    void shouldReturnMapFromYamlFileInClassLoader() {
        final Map<String, Object> expected = new HashMap<String, Object>() {{
            put("string", "value");
            put("integer", 1);
            put("integerArray", new ArrayList<Integer>() {{
                add(1);
                add(2);
                add(3);
            }});
        }};

        final Map<String, Object> actual = (Map<String, Object>) yamlToObjectTransformer.getTransformedObject(FileSource.CLASS_LOADER, "valid.yml", Map.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnValidObjectFromYamlFileInClassLoader() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlToObjectTransformer.getTransformedObject(FileSource.CLASS_LOADER, "valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnValidObjectFromYamlFileInFilesystem() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlToObjectTransformer.getTransformedObject(FileSource.FILESYSTEM, "external/test/resources/ymls/valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowYamlFileNotFoundExceptionThenNonExistentFileGiven() {
        assertThrows(YamlFileNotFoundException.class,
                () -> yamlToObjectTransformer.getTransformedObject(FileSource.FILESYSTEM, "valid.yml", SimplePojo.class));
    }

    @Test
    void shouldThrowYAMLExceptionThenInvalidStructureFileGiven() {
        assertThrows(YAMLException.class,
                () -> yamlToObjectTransformer.getTransformedObject(FileSource.CLASS_LOADER, "invalid.yml", SimplePojo.class));
    }
}