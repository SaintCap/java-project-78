package hexlet.code.schemas;

import java.util.function.Predicate;

public interface Schema<T> {
    void addCheck(SchemaChecks checkName, Predicate<T> predicate);
    boolean isValid(T value);
}
