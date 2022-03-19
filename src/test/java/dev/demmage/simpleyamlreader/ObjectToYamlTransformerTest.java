package dev.demmage.simpleyamlreader;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ObjectToYamlTransformerTest {

    private static ObjectToYamlTransformer objectToYamlTransformer;

    private static final String FILENAME = "file.yml";

    @BeforeAll
    static void init() {
        objectToYamlTransformer = new ObjectToYamlTransformer();
    }

    @Test
    void shouldWriteObjectStructureToFile() {
        final SimplePojo pojo = new SimplePojo("value", 5, new int[]{1, 3, 5});

        objectToYamlTransformer.transformToFile(FILENAME, pojo);

        assertTrue(Files.exists(Paths.get(FILENAME)));
    }

    @AfterAll
    @SneakyThrows
    static void cleanup() {
        Files.delete(Paths.get(FILENAME));
    }
}