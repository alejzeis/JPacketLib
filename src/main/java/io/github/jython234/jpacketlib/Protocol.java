package io.github.jython234.jpacketlib;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a protocol, with packets.
 */
public class Protocol {
    private final String name;
    private final int version;

    private Map<Integer, PacketDefinition> packets = new HashMap<>();

    public Protocol(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public void addPacketDef(PacketDefinition packet) {
        packets.put(packet.getID(), packet);
    }

    public PacketDefinition getPacketDef(int id) {
        return packets.get(id);
    }

    public String getName() {
        return name;
    }

    public int getVersion() {
        return version;
    }
}
