package io.github.jython234.jpacketlib;

import io.github.jython234.jpacketlib.packet.PacketDefinition;
import io.github.jython234.jpacketlib.types.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a protocol, with packets.
 */
public class Protocol {
    private final String name;
    private final int version;

    private Map<Integer, PacketDefinition> packets = new HashMap<>();
    private Map<String, Constant> constants = new HashMap<>();

    public Protocol(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public void addPacketDef(PacketDefinition packet) {
        packets.put(packet.getID(), packet);
    }

    public void addConstant(Constant constant) {
        constants.put(constant.getName(), constant);
    }

    public PacketDefinition getPacketDef(int id) {
        return packets.get(id);
    }

    public Constant getConstant(String name) {
        return constants.get(name);
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }
}
