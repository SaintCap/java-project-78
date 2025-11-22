package hexlet.code.schemas;

import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
public final class NumberSchema extends BaseSchema<Integer> {
    private boolean isRequired = false;

    @Override
    public NumberSchema required() {
        this.isRequired = true;
        addCheckFirst(SchemaChecks.REQUIRED, Objects::nonNull);
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
