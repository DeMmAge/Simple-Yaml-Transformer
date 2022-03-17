package dev.demmage.simpleyamlreader;

import dev.demmage.simpleyamlreader.exception.YamlFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.yaml.snakeyaml.error.YAMLException;

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
    void shouldReturnValidObjectFromYamlFileInClassLoader() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlTransformer.getTransformedObjectFromClassLoader("valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnValidObjectFromYamlFileInFilesystem() {
        final SimplePojo expected = new SimplePojo("value", 1, new int[]{1, 2, 3});
        final SimplePojo actual = yamlTransformer.getTransformedObjectFromDirectory("external_test_resources/ymls/valid.yml", SimplePojo.class);

        log.info(actual.toString());

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowYamlFileNotFoundExceptionThenNonExistentFileGiven() {
        assertThrows(YamlFileNotFoundException.class,
                () -> yamlTransformer.getTransformedObjectFromClassLoader("non-existent.yml", SimplePojo.class));
    }

    @Test
    void shouldThrowYAMLExceptionThenInvalidStructureFileGiven() {
        assertThrows(YAMLException.class,
                () -> yamlTransformer.getTransformedObjectFromClassLoader("invalid.yml", SimplePojo.class));
    }
}