package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public final  class ValidatorTest {
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
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("Jo pa"));
        assertFalse(schema.isValid("Jo pa pa"));
    }

    @Test
    public void testNumberSchemaBuild() {
        var min = 5;
        var max = 10;
        var schema = new NumberSchema();

        schema.required()
                .positive()
                .range(min, max);

        var checks = schema.getChecks();
        assertNotNull(checks);
        assertEquals(3, checks.size());
    }

    @ParameterizedTest
    @MethodSource("forNullConfigNumberSchema")
    public void testNumberSchemaValidNull(NumberSchema schema) {
        assertTrue(schema.isValid(null));
    }

    @ParameterizedTest
    @MethodSource("basicConfigNumberSchema")
    public void testNumberSchemaValid(NumberSchema schema) {
        assertTrue(schema.isValid(6));
    }

    @ParameterizedTest
    @MethodSource("basicConfigNumberSchema")
    public void testNumberSchemaNotValid(NumberSchema schema) {
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(11));
        assertFalse(schema.isValid(-1));
    }

    @Test
    public void testMapSchemaBasic() {
        var v = new Validator();

        var schema = v.map();

        assertTrue(schema.isValid(null)); // true

        schema.required();

        assertFalse(schema.isValid(null)); // false
        assertTrue(schema.isValid(new HashMap<>())); // true
        var data = new HashMap<String, String>();
        data.put("key1", "value1");
        assertTrue(schema.isValid(data)); // true

        schema.sizeof(2);

        assertFalse(schema.isValid(data));  // false
        data.put("key2", "value2");
        assertTrue(schema.isValid(data)); // true
    }

    @Test
    public void testMapSchemaFinal() {
        var v = new Validator();

        var schema = v.map();
        Map<String, BaseSchema<String>> schemas = new HashMap<>();
        StringSchema stringSchema1 = v.string();

        schemas.put("firstName", stringSchema1.required());

        StringSchema stringSchema2 = v.string();
        schemas.put("lastName", stringSchema2.required().minLength(2));

        schema.shape(schemas);

        Map<String, String> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        assertTrue(schema.isValid(human1)); // true

        Map<String, String> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", null);
        assertFalse(schema.isValid(human2)); // false

        Map<String, String> human3 = new HashMap<>();
        human3.put("firstName", "Anna");
        human3.put("lastName", "B");
        assertFalse(schema.isValid(human3)); // false
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

    private static Stream<Arguments> forNullConfigNumberSchema() {
        var schema = new NumberSchema();
        var schema2 = new NumberSchema();
        schema2.positive();
        return Stream.of(
                Arguments.of(schema
                ),
                Arguments.of(schema2
                )
        );
    }

    private static Stream<Arguments> basicConfigNumberSchema() {
        var min = 5;
        var max = 10;
        var schema = new NumberSchema();

        schema.required()
                .positive()
                .range(min, max);

        return Stream.of(
                Arguments.of(schema
                )
        );
    }

}
