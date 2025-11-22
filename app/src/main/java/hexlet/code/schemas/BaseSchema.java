package hexlet.code.schemas;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.function.Predicate;

@Getter
public abstract class BaseSchema<T>{
    private final LinkedHashMap<SchemaChecks, Predicate<T>> checks = new LinkedHashMap<>();

    public void addCheck(SchemaChecks checkName, Predicate<T> predicate) {
        checks.put(checkName, predicate);
    }

    public void addCheckFirst(SchemaChecks checkName, Predicate<T> predicate) {
        checks.putFirst(checkName, predicate);
    }

    public boolean isValid(T value) {
        return checks.values().stream().allMatch(predicate -> predicate.test(value));
    }

    public abstract BaseSchema<T> required();
}
