package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class YamlTransformerTest {

    private static YamlTransformer yamlTransformer;

    @BeforeAll
    static void init() {
        yamlTransformer = new YamlTransformer();
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

        final Map<String, Object> actual = (Map<String, Object>) yamlTransformer.getTransformedObject(FileSource.CLASS_LOADER, "valid.yml", Map.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnValidObjectFromYamlFileInClassLoader() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlTransformer.getTransformedObject(FileSource.CLASS_LOADER, "valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnValidObjectFromYamlFileInFilesystem() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlTransformer.getTransformedObject(FileSource.FILESYSTEM, "external/test/resources/ymls/valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnValidObjectFromYamlFileInClassLoaderViaDeprecatedMethod() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlTransformer.getTransformedObjectFromClassLoader("valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }
    @Test
    void shouldReturnValidObjectFromYamlFileInFileSystemViaDeprecatedMethod() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlTransformer.getTransformedObjectFromDirectory("external/test/resources/ymls/valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowYamlFileNotFoundExceptionThenNonExistentFileGiven() {
        assertThrows(YamlFileNotFoundException.class,
                () -> yamlTransformer.getTransformedObject(FileSource.FILESYSTEM, "valid.yml", SimplePojo.class));
    }

    @Test
    void shouldThrowYAMLExceptionThenInvalidStructureFileGiven() {
        assertThrows(YAMLException.class,
                () -> yamlTransformer.getTransformedObject(FileSource.CLASS_LOADER, "invalid.yml", SimplePojo.class));
    }
}