package io.github.jython234.jpacketlib;

/**
 * Represents different types of fields.
 */
public enum FieldType {
    BYTE("byte", byte.class),
    BOOL("bool", boolean.class),
    SHORT("int16", short.class),
    USHORT("uint16", int.class),
    INTEGER("int32", int.class),
    UINTEGER("uint32", long.class),
    LONG("int64", long.class),
    FLOAT("float", float.class),
    DOUBLE("double", double.class),
    INT24("int24", int.class),
    UINT24("uint24", int.class),
    STRING("str", String.class);

    private String type;
    private Class asClass;

    FieldType(String type, Class asClass) {
        this.type = type;
        this.asClass = asClass;
    }

    public static FieldType parseType(String string) {
        if(string.equals("int16")) {
            return FieldType.valueOf("SHORT");
        } else if(string.equals("uint16")) {
            return FieldType.valueOf("USHORT");
        } else if(string.equals("int32")) {
            return FieldType.valueOf("INTEGER");
        } else if(string.equals("uint32")) {
            return FieldType.valueOf("UINTEGER");
        } else if(string.equals("int64")) {
            return FieldType.valueOf("LONG");
        } else if(string.equals("str")) {
            return FieldType.valueOf("STRING");
        } else {
            return FieldType.valueOf(string.toUpperCase());
        }
    }

    public Class getAsClass() {
        return asClass;
    }
}
