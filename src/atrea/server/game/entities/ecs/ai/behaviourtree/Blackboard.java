package atrea.server.game.entities.ecs.ai.behaviourtree;

import atrea.server.engine.utilities.Position;

import java.util.HashMap;
import java.util.Map;

public class Blackboard {
    private Map<String, Object> genericValues;
    private Map<String, Integer> integerValues;
    private Map<String, String> stringValues;
    private Map<String, Boolean> booleanValues;
    private Map<String, Position> positionValues;

    public Blackboard() {
        this.integerValues = new HashMap<>();
        this.stringValues = new HashMap<>();
        this.booleanValues = new HashMap<>();
        this.positionValues = new HashMap<>();
        this.genericValues = new HashMap<>();
    }

    public Position getPositionValue(String key) { return positionValues.get(key); }
    public void setPositionValue(String key, Position value) { positionValues.put(key, value); }
    public int getIntegerValue(String key) { return integerValues.get(key); }
    public void setIntegerValue(String key, int value) { integerValues.put(key, value); }
    public String getStringValue(String key) { return stringValues.get(key); }
    public void setStringValue(String key, String value) { stringValues.put(key, value); }
    public boolean getBooleanValue(String key) { return booleanValues.get(key); }
    public void setBooleanValue(String key, boolean value) { booleanValues.put(key, value); }
    public <T> T getGenericValue(String key, Class<T> type) { return (T) genericValues.get(key); }
    public void setGenericValue(String key, Object value) { genericValues.put(key, value); }
}