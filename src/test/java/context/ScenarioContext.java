package context;

import java.util.HashMap;
import java.util.Map;

public class ScenarioContext {

    private static ScenarioContext instance;
    private final Map<String, Object> context;

    private ScenarioContext() {
        context = new HashMap<>();
    }

    public static ScenarioContext getInstance() {
        if (instance == null) {
            synchronized (ScenarioContext.class) {
                if (instance == null) {
                    instance = new ScenarioContext();
                }
            }
        }
        return instance;
    }

    public void setContext(String key, Object value) {
        context.put(key, value);
    }

    public Object getContext(String key) {
        return context.get(key);
    }

    public boolean isContains(String key) {
        return context.containsKey(key);
    }

    public void clear() {
        context.clear();
    }
}

