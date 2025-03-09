//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class JsonParserTest {
//    // TODO (maybe)
//    private final String configPath = "configExample.json";
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final String outputPath = "output.json";
//
//    private Map<String, Object> parseJsonOutput() throws IOException {
//        return objectMapper.readValue(Files.readString(Paths.get(outputPath)), Map.class);
//    }
//
//    @AfterEach
//    void cleanUp() throws IOException {
//        Files.deleteIfExists(Paths.get(outputPath));
//    }
//
//    @Test
//    void testConfig1_DirMode_StringAction() throws Exception {
//        String[] args = {configPath, "1", "-p", outputPath, "-iew"};
//        JsonParser.main(args);
//
//        Map<String, Object> output = parseJsonOutput();
//
//        assertEquals(configPath, output.get("configFile"));
//        assertEquals(1, output.get("configurationID"));
//
//        Map<String, Object> out = (Map<String, Object>) output.get("out");
//
//        assertNotNull(out);
//
//        assertTrue(out.containsKey("1"));
//        assertTrue(out.containsKey("2"));
//        assertTrue(out.containsKey("3"));
//
//        Map<String, String> out1 = (Map<String, String>) out.get("1");
//        assertEquals("string1 taken from file1", out1.get("1"));
//        assertEquals("string1 taken from file2", out1.get("2"));
//        assertEquals("string1 taken from file3", out1.get("3"));
//
//        // Проверка содержимого "2"
//        Map<String, String> out2 = (Map<String, String>) out.get("2");
//        assertEquals("string2 taken from file1", out2.get("1"));
//        assertEquals("string2 taken from file2", out2.get("2"));
//        assertEquals("string2 taken from file3", out2.get("3"));
//
//        // Проверка содержимого "3"
//        Map<String, String> out3 = (Map<String, String>) out.get("3");
//        assertEquals("string3 taken from file1", out3.get("1"));
//        assertEquals("", out3.get("2")); // Проверка пустой строки
//        assertEquals("string3 taken from file3", out3.get("3"));
//    }
//
//    @Test
//    void testConfig2_FilesMode_CountAction() throws Exception {
//        String[] args = {configPath, "2", "-p", outputPath, "-iew"};
//        JsonParser.main(args);
//
//        Map<String, Object> output = parseJsonOutput();
//
//        assertEquals(2, output.get("configurationID"));
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testConfig3_DirMode_ReplaceAction() throws Exception {
//        String[] args = {configPath, "3", "-p", outputPath};
//        JsonParser.main(args);
//
//        Map<String, Object> output = parseJsonOutput();
//
//        assertEquals(3, output.get("configurationID"));
//        assertNotNull(output.get("out"));
//        // Проверить замену символов по алгоритму
//    }
//
//    @Test
//    void testConfig4_FilesMode_StringAction() throws Exception {
//        String[] args = {configPath, "4", "-p", outputPath};
//        JsonParser.main(args);
//
//        Map<String, Object> output = parseJsonOutput();
//
//        assertEquals("4", output.get("configurationID"));
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testConfig5_DirMode_CountAction() throws Exception {
//        String[] args = {configPath, "5", "-p", outputPath};
//        JsonParser.main(args);
//
//        Map<String, Object> output = parseJsonOutput();
//
//        assertEquals("5", output.get("configurationID"));
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testInvalidConfigId() {
//        String[] args = {configPath, "99", "-p", outputPath};
//        Exception exception = assertThrows(Exception.class, () -> JsonParser.main(args));
//        assertTrue(exception.getMessage().contains("Configuration not found"));
//    }
//
//    @Test
//    void testMissingConfigFile() {
//        String[] args = {"./wrongConfig.json", "1", "-p", outputPath};
//        Exception exception = assertThrows(Exception.class, () -> JsonParser.main(args));
//        assertTrue(exception.getMessage().contains("No such file"));
//    }
//
//    @Test
//    void testMissingPathParameter() {
//        String[] args = {configPath, "1"};
//        Exception exception = assertThrows(Exception.class, () -> JsonParser.main(args));
//        assertTrue(exception.getMessage().contains("Missing required option"));
//    }
//
//    @Test
//    void testOutputFileCreation() throws Exception {
//        String[] args = {configPath, "1", "-p", outputPath};
//        JsonParser.main(args);
//        assertTrue(Files.exists(Paths.get(outputPath)));
//    }
//
//    @Test
//    void testEmptyFilesInDirMode() throws Exception {
//        String[] args = {configPath, "1", "-p", outputPath};
//        JsonParser.main(args);
//        Map<String, Object> output = parseJsonOutput();
//
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testSingleFileProcessing() throws Exception {
//        String[] args = {configPath, "4", "-p", outputPath};
//        JsonParser.main(args);
//        Map<String, Object> output = parseJsonOutput();
//
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testMultipleFilesProcessing() throws Exception {
//        String[] args = {configPath, "2", "-p", outputPath};
//        JsonParser.main(args);
//        Map<String, Object> output = parseJsonOutput();
//
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testReplaceActionProcessing() throws Exception {
//        String[] args = {configPath, "3", "-p", outputPath};
//        JsonParser.main(args);
//        Map<String, Object> output = parseJsonOutput();
//
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testCountActionProcessing() throws Exception {
//        String[] args = {configPath, "5", "-p", outputPath};
//        JsonParser.main(args);
//        Map<String, Object> output = parseJsonOutput();
//
//        assertNotNull(output.get("out"));
//    }
//
//    @Test
//    void testIncorrectJsonFormat() throws Exception {
//        Path corruptConfig = Paths.get("./corruptConfig.json");
//        Files.writeString(corruptConfig, "{invalid json}");
//        String[] args = {corruptConfig.toString(), "1", "-p", "output_corrupt.json"};
//        Exception exception = assertThrows(Exception.class, () -> JsonParser.main(args));
//        assertTrue(exception.getMessage().contains("JsonProcessingException"));
//        Files.deleteIfExists(corruptConfig);
//    }
//}
