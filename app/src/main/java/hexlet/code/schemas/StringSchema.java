package hexlet.code.schemas;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.function.Predicate;

@NoArgsConstructor
@Getter
public class StringSchema implements Schema<String> {
    private final LinkedHashMap<SchemaChecks, Predicate<String>> checks = new LinkedHashMap<>();

    @Override
    public void addCheck(SchemaChecks checkName, Predicate<String> predicate) {
        checks.put(checkName, predicate);
    }

    @Override
    public boolean isValid(String value) {
        return checks.values().stream().allMatch(predicate -> predicate.test(value));
    }

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
