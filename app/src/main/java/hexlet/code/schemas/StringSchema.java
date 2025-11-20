package hexlet.code.schemas;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class StringSchema extends SchemaChecksContainer<String> {

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
