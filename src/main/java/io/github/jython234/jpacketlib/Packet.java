package io.github.jython234.jpacketlib;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a decoded or encoded packet with fields.
 */
public class Packet {
    private Map<String, Field> fields = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getFieldValue(String name, Class<T> clazz) {
        if(! fields.containsKey(name)) return null;
        return (T) fields.get(name);
    }

    public void setFieldValue(String name, Object value, FieldType type ) {
        fields.put(name, new Field(value, type));
    }

    public byte[] encode(PacketDefinition def) {
        ByteBuffer bb;
        if(def.getLength() == -1) bb = ByteBuffer.allocate(2048);
        else bb = ByteBuffer.allocate(def.getLength());

        bb.put((byte) def.getID());
        def.getOrderOfFields().forEach(fieldName -> {
            Field field = fields.get(fieldName);
            switch (field.type) {
                case BYTE:
                    bb.put((byte) field.value);
                    break;
                case BYTES:
                    bb.put((byte[]) field.value);
                    break;
                case SHORT:
                case USHORT:
                    bb.putShort((short) field.value);
                    break;
                case INT24:
                    //TODO
                    bb.position(bb.position() + 3);
                    break;
                case UINT24:
                    putLTriad(bb, (int) field.value);
                    break;
                case INTEGER:
                case UINTEGER:
                    bb.putInt((int) field.value);
                    break;
                case LONG:
                    bb.putLong((long) field.value);
                    break;
                case FLOAT:
                    bb.putFloat((float) field.value);
                    break;
                case DOUBLE:
                    bb.putDouble((double) field.value);
                    break;
                case STRING:
                    putString(bb, field);
                    break;
            }
        });

        return bb.array();
    }

    public void decode(byte[] data, PacketDefinition def) {
        ByteBuffer bb = ByteBuffer.wrap(data);
        if(bb.get() != def.getID()) throw new IllegalArgumentException("Invalid data: PID does not match");

        def.getOrderOfFields().forEach(fieldName -> {
            FieldType type = def.getFieldType(fieldName);

            Object value = null;
            switch (type) {
                case BYTE:
                    value = bb.get();
                    break;
                case BYTES:
                    value = new byte[type.getLength()];
                    bb.get((byte[]) value);
                    break;
                case SHORT:
                    value = bb.getShort();
                    break;
                case USHORT:
                    value = (int) bb.getShort() & 0xFFFF;
                    break;
                case INT24:
                    //TODO
                    bb.position(bb.position() + 3);
                    break;
                case UINT24:
                    value = getLTriad(bb);
                    break;
                case INTEGER:
                    value = bb.getInt();
                case UINTEGER:
                    value = (long) bb.getInt() & 0xFFFFFF;
                    break;
                case LONG:
                    value = bb.getLong();
                    break;
                case FLOAT:
                    value = bb.getFloat();
                    break;
                case DOUBLE:
                    value = bb.getDouble();
                    break;
                case STRING:
                    value = getString(bb, type.getLength());
                    break;
            }
            fields.put(fieldName, new Field(value, type));
        });
    }

    public void putString(ByteBuffer bb, Field field) {
        String s = (String) field.value;
        byte[] bytes = s.getBytes(Charset.forName("UTF-8"));
        switch (field.type.getLength()){
            case 1:
                bb.put((byte) bytes.length);
                break;
            case 2:
                bb.putShort((short) bytes.length);
                break;
            case 4:
                bb.putInt(bytes.length);
                break;

        }
        bb.put(bytes);
    }

    public String getString(ByteBuffer bb, int lengthType) {
        int len = 0;
        switch (lengthType) {
            case 1:
                len = bb.get();
                break;
            case 2:
                len = bb.getShort();
                break;
            case 4:
                len = bb.getInt();
                break;
        }
        byte[] bytes = new byte[len];
        bb.get(bytes);
        return new String(bytes, Charset.forName("UTF-8"));
    }

    public void putLTriad(ByteBuffer bb, int t) {
        bb.put((byte)(t & 0xFF));
        bb.put((byte)((t >> 8) & 0xFF));
        bb.put((byte)((t >> 16) & 0xFF));
    }

    public int getLTriad(ByteBuffer bb) {
        return (bb.get() & 0xFF) | ((bb.get() & 0xFF) << 8) | ((bb.get() & 0x0F) << 16);
    }

    public static class Field {
        public Object value;
        public FieldType type;

        public Field(Object value, FieldType type) {
            this.value = value;
            this.type = type;
        }
    }
}
