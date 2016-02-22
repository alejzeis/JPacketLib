package io.github.jython234.jpacketlib.types;

/**
 * Created by atzei on 2/21/2016.
 */
public enum ConstantType {
    STRING("string", String.class),
    INTEGER("integer", int.class),
    DECIMAL("decimal", double.class);

    private String type;
    private Class jType;

    ConstantType(String type, Class jType) {
        this.type = type;
        this.jType = jType;
    }

    public String getTypeAsString() {
        return type;
    }

    public Class getTypeAsClass() {
        return jType;
    }
}
