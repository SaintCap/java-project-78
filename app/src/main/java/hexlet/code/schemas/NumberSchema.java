package hexlet.code.schemas;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.function.Predicate;

@NoArgsConstructor
@Getter
public final class NumberSchema implements BaseSchema<Integer> {
    private final LinkedHashMap<SchemaChecks, Predicate<Integer>> checks = new LinkedHashMap<>();
    private boolean isRequired = false;

    @Override
    public void addCheck(SchemaChecks checkName, Predicate<Integer> predicate) {
        checks.put(checkName, predicate);
    }

    @Override
    public boolean isValid(Integer value) {
        return checks.values().stream().allMatch(predicate -> predicate.test(value));
    }

    public NumberSchema required() {
        this.isRequired = true;
        addCheck(SchemaChecks.REQUIRED, Objects::nonNull);
        return this;
    }

    public NumberSchema positive() {
        addCheck(SchemaChecks.POSITIVE, isRequired
                                        ? s -> s > 0
                                        : s -> s == null || s > 0);
        return this;
    }

    public NumberSchema range(int min, int max) {
        addCheck(SchemaChecks.RANGE, s -> s >= min && s <= max);
        return this;
    }
}
