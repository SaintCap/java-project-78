package hexlet.code.schemas;

import java.util.Objects;

public class NumberSchema extends SchemaChecksContainer<Integer> {
    private boolean isRequired = false;

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
