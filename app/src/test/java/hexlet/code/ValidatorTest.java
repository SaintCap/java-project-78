package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorTest {
    @Test
    public void testStringSchemaBuild() {
        var min = 5;
        var substring = "Hel";
        var schema = new StringSchema();

        schema.required()
              .minLength(min)
              .contains(substring);

        var checks = schema.getChecks();
        assertNotNull(checks);
        assertEquals(3, checks.size());
    }

    @ParameterizedTest
    @MethodSource("forNullConfigStringSchema")
    public void testStringSchemaValidNull(StringSchema schema) {
        assertTrue(schema.isValid(null));
    }

    @ParameterizedTest
    @MethodSource("basicConfigStringSchema")
    public void testStringSchemaValid(StringSchema schema) {
        assertTrue(schema.isValid("Hello, world!"));
    }

    @ParameterizedTest
    @MethodSource("basicConfigStringSchema")
    public void testStringSchemaNotValid(StringSchema schema) {
        assertFalse(schema.isValid("Jo pa"));
    }

    @ParameterizedTest
    @MethodSource("basicConfigStringSchema")
    public void testStringSchemaNotValidNull(StringSchema schema) {
        assertFalse(schema.isValid(null));
    }

    private static Stream<Arguments> forNullConfigStringSchema() {
        var schema = new StringSchema();
        return Stream.of(
                Arguments.of(schema
                )
        );
    }

    private static Stream<Arguments> basicConfigStringSchema() {
        var min = 5;
        var substring = "Hel";
        var schema = new StringSchema();

        schema.required()
              .minLength(min)
              .contains(substring);

        return Stream.of(
                Arguments.of(schema
                )
        );
    }
}