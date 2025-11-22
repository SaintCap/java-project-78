package hexlet.code.schemas;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.function.Predicate;

@Getter
public abstract class BaseSchema<T> {
    private final LinkedHashMap<SchemaChecks, Predicate<T>> checks = new LinkedHashMap<>();

    /**
     * Adds a validation check to the schema.
     * When overriding this method in subclasses, ensure that:
     * - The check name is unique
     * - The predicate is not null
     * - Super method is called if additional logic is added
     *
     * @param checkName the name of the check
     * @param predicate the validation predicate
     */
    public void addCheck(SchemaChecks checkName, Predicate<T> predicate) {
        checks.put(checkName, predicate);
    }

    /**
     * Adds a validation check to the beginning of the checks list.
     * When overriding, note that this check will be executed first.
     * Always call super.addCheckFirst() when extending this method.
     *
     * @param checkName the name of the check
     * @param predicate the validation predicate
     */
    public void addCheckFirst(SchemaChecks checkName, Predicate<T> predicate) {
        checks.putFirst(checkName, predicate);
    }

    /**
     * Validates the value against all registered checks.
     * Overriding implementations should:
     * - Call super.isValid() for base validation
     * - Add additional checks after the base validation
     * - Return combined result of all checks
     *
     * @param value the value to validate
     * @return true if all checks pass, false otherwise
     */
    public boolean isValid(T value) {
        return checks.values().stream().allMatch(predicate -> predicate.test(value));
    }

    public abstract BaseSchema<T> required();
}
