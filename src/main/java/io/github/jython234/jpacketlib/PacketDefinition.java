package io.github.jython234.jpacketlib;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a packet in a protocol with fields.
 */
public class PacketDefinition {
    private final int ID;
    private final String name;
    private Map<String, FieldType> fields;

    public PacketDefinition(int id, String name, Map<String, FieldType> fields) {
        this.name = name;
        this.fields = fields;
        this.ID = id;
    }

    public PacketDefinition(int id, String name) {
        this.name = name;
        this.fields = new HashMap<String, FieldType>();
        this.ID = id;
    }

    public FieldType getFieldType(String name) {
        return fields.get(name);
    }

    public boolean hasField(String name) {
        return fields.containsKey(name);
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }
}
