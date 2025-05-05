package cl.tenpo.learning.reactive.tasks.task2.domain.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class TestFileUtils {
    public static final Path TEST_DIR = Path.of("src/test/resources");
    private static final ObjectMapper MAPPER =
        new ObjectMapper()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .registerModule(new JavaTimeModule());

    //* model *//
    public static final String AUTHORIZED_USER_MODEL_TEST_FILE = "model/authorized_user.json";
    public static final String PERCENTAGE_MODEL_TEST_FILE = "model/percentage.json";

    //* dto *//
    public static final String CALCULATION_CREATE_REQUEST_DTO_TEST_FILE = "dto/calculation_create_request.json";

    private TestFileUtils() {
    }

    public static <T> T readFile(final String path, final Class<T> valueType) throws IOException {
        final String json = Files.readString(TEST_DIR.resolve(path));
        return MAPPER.readValue(json, valueType);
    }
}
