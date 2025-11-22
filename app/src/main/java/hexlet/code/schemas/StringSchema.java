package hexlet.code.schemas;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class StringSchema extends BaseSchema<String> {

    @Override
    public StringSchema required() {
        addCheckFirst(SchemaChecks.REQUIRED, s -> s != null && !s.isEmpty());
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
