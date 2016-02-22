package io.github.jython234.jpacketlib.packet;

import io.github.jython234.jpacketlib.types.FieldType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a packet in a protocol with fields.
 */
public class PacketDefinition {
    private final int ID;
    private final String name;
    private final int length;
    private List<String> order;
    private Map<String, FieldType> fields;

    public PacketDefinition(int id, String name, int length, Map<String, FieldType> fields, List<String> order) {
        this.name = name;
        this.length = length;
        this.fields = fields;
        this.order = order;
        this.ID = id;
    }

    public PacketDefinition(int id, String name) {
        this.name = name;
        this.length = -1;
        this.fields = new HashMap<String, FieldType>();
        this.order = new ArrayList<>();
        this.ID = id;
    }

    public FieldType getFieldType(String name) {
        return fields.get(name);
    }

    public List<String> getOrderOfFields() {
        return order;
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

    public int getLength() {
        return length;
    }
}
