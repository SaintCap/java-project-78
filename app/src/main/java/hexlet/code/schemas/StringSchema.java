package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.function.Predicate;

@NoArgsConstructor
public class StringSchema extends SchemaChecksContainer<String> {
    private final LinkedHashMap<SchemaChecks, Predicate<String>> checks = new LinkedHashMap<>();

    public StringSchema required() {
        addCheck(SchemaChecks.REQUIRED, s -> s != null && !s.isEmpty());
        return this;
    }

    public StringSchema minLength(int min) {
        addCheck(SchemaChecks.MIN_LENGTH, s -> s.length() >= min);
        return this;
    }

    public StringSchema contains(String substring) {
        addCheck(SchemaChecks.CONTAINS, s -> s.contains(substring));
        return this;
    }

}
