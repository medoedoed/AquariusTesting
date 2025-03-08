import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {
    private static final String CONFIG_PATH = "/home/medo/projects/AquariusTesting/configExample.json";

    @BeforeEach
    void setUp() {
        File configFile = new File(CONFIG_PATH);
        assertTrue(configFile.exists(), "Config file should exist at path: " + CONFIG_PATH);
    }

    @Test
    void testRun_withValidConfig_shouldHandleConfig() {
        // Arrange
        String[] args = {CONFIG_PATH, "1"};

        // Act
        JsonParser jsonHandler = new JsonParser();
        int exitCode = new CommandLine(jsonHandler).execute(args);

        // Assert
        assertEquals(0, exitCode, "Exit code should be 0 on successful execution");
    }

    @Test
    void testRun_withInvalidFilePath_shouldReturnError() {
        // Arrange
        String invalidPath = "/home/medo/projects/AquariusTesting/InvalidConfig.json";
        String[] args = {invalidPath, "1"};

        // Act
        JsonParser jsonParser = new JsonParser();
        int exitCode = new CommandLine(jsonParser).execute(args);

        // Assert
        assertNotEquals(0, exitCode, "Exit code should not be 0 for invalid file path");
    }

    @Test
    void testConfigFileContent_shouldMatchExpected() throws Exception {
        // Arrange
        String[] args = {CONFIG_PATH, "1"};

        // Act: Read file content for validation
        String content = new String(Files.readAllBytes(Paths.get(CONFIG_PATH)));

        // Assert
        assertTrue(content.contains("\"configurations\": ["), "Config file should contain 'configurations' array");
        assertTrue(content.contains("\"id\": 1"), "Config file should contain the expected config ID");
    }
}
