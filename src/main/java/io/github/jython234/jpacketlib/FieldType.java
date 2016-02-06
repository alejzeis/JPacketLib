package io.github.jython234.jpacketlib;

/**
 * Represents different types of fields.
 */
public enum FieldType {
    BYTE("byte", byte.class, 1),
    BOOL("bool", boolean.class, 1),
    SHORT("int16", short.class, 2),
    USHORT("uint16", int.class, 2),
    INTEGER("int32", int.class, 4),
    UINTEGER("uint32", long.class, 4),
    LONG("int64", long.class, 8),
    FLOAT("float", float.class, 4),
    DOUBLE("double", double.class, 8),
    INT24("int24", int.class, 3),
    UINT24("uint24", int.class, 3),
    STRING("str", String.class, 2), //Length here specifies how many bytes to read to get the string length
    BYTES("bytes", byte[].class, -1);

    private String type;
    private Class asClass;
    private int len;

    FieldType(String type, Class asClass, int len) {
        this.type = type;
        this.asClass = asClass;
        this.len = len;
    }

    public void setLength(int len) {
        this.len = len;
    }

    public int getLength() {
        return len;
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

    @Override
    public String toString() {
        return type;
    }
}
