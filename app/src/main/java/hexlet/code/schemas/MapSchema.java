package hexlet.code.schemas;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

@NoArgsConstructor
@Getter
public class MapSchema implements BaseSchema<Map<String, String>> {
    private final LinkedHashMap<SchemaChecks, Predicate<Map<String, String>>> checks = new LinkedHashMap<>();
    private Map<String, BaseSchema<String>> schemas = new HashMap<>();

    @Override
    public void addCheck(SchemaChecks checkName, Predicate<Map<String, String>> predicate) {
        checks.put(checkName, predicate);
    }

    @Override
    public boolean isValid(Map<String, String> value) {
        var result = checks.values().stream().allMatch(predicate -> predicate.test(value));
        if (result && !schemas.isEmpty()) {
            result = value.keySet()
                          .stream()
                          .allMatch(k -> {
                                      if (!schemas.containsKey(k)) {
                                          return true;
                                      }
                                      return schemas.get(k).isValid(value.get(k));
                                  }
                          );   
        }
        return result;
    }

    public MapSchema required() {
        addCheck(SchemaChecks.REQUIRED, Objects::nonNull);
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck(SchemaChecks.SIZE, s -> s.size() == size);
        return this;
    }

    public MapSchema shape(Map<String, BaseSchema<String>> schemas) {
        this.schemas = new HashMap<>(schemas);
        return this;
    }

}
