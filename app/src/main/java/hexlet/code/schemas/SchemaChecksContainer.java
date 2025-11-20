package hexlet.code.schemas;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.function.Predicate;

@Getter
abstract class SchemaChecksContainer<T> {
    private final LinkedHashMap<SchemaChecks, Predicate<T>> checks = new LinkedHashMap<>();

    public void addCheck(SchemaChecks checkName, Predicate<T> predicate) {
        checks.put(checkName, predicate);
    }

    public boolean isValid(T value) {
        return checks.values().stream().allMatch(predicate -> predicate.test(value));
    }

}
