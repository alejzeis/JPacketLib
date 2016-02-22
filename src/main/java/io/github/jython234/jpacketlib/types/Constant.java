package io.github.jython234.jpacketlib.types;

/**
 * Created by atzei on 2/21/2016.
 */
public class Constant {
    private String name;
    private ConstantType type;
    private Object value;

    public Constant(String name, ConstantType type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public ConstantType getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
