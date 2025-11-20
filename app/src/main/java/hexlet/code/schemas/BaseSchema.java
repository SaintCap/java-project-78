package hexlet.code.schemas;

import java.util.function.Predicate;

public interface BaseSchema<T> {
    void addCheck(SchemaChecks checkName, Predicate<T> predicate);
    boolean isValid(T value);
}
