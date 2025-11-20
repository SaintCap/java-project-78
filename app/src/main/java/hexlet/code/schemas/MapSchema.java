package hexlet.code.schemas;

import java.util.Map;
import java.util.Objects;

public class MapSchema extends SchemaChecksContainer<Map<String, String>> {

    public MapSchema required() {
        addCheck(SchemaChecks.REQUIRED, Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck(SchemaChecks.SIZE, s -> s.size() == size);
        return this;
    }
}
