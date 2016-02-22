package io.github.jython234.jpacketlib;

import io.github.jython234.jpacketlib.packet.PacketDefinition;
import io.github.jython234.jpacketlib.types.Constant;
import io.github.jython234.jpacketlib.types.ConstantType;
import io.github.jython234.jpacketlib.types.FieldType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that loads and parses packet definitions. (.NPD)
 */
public class DefinitionParser {

    public static Protocol parseDocument(File file) {
        Protocol protocol = null;
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(file);
            Element root = doc.getDocumentElement();
            root.normalize();
            protocol = new Protocol(root.getAttribute("name"), Integer.parseInt(root.getAttribute("version")));
            //TODO: Check Exceptions
            NodeList packets = doc.getElementsByTagName("packet");
            for(int i = 0; i < packets.getLength(); i++) {
                Element element = (Element) packets.item(i);
                element.normalize();
                String pkName = element.getAttribute("name");
                int pid = Integer.parseInt(element.getElementsByTagName("pid").item(0).getTextContent(), 16);
                int length = Integer.parseInt(element.getElementsByTagName("length").item(0).getTextContent());
                if(length < -1) throw new ParseException("Packet Length must be positive or -1");

                Map<String, FieldType> fieldMap = new HashMap<>();
                List<String> order = new ArrayList<>();

                NodeList fields = element.getElementsByTagName("field");
                for(int i2 = 0; i2 < fields.getLength(); i2++) {
                    Element field = (Element) fields.item(i2);
                    field.normalize();
                    String fieldName = field.getAttribute("name");
                    String fieldType = field.getAttribute("type");
                    order.add(i2, fieldName);
                    if(fieldType.equals(FieldType.BYTES.toString())) {
                        if(!field.hasAttribute("length")) throw new ParseException("Length not specified for fieldType \"bytes\"");
                        FieldType f = FieldType.BYTES;
                        f.setLength(Integer.parseInt(field.getAttribute("length")));
                        fieldMap.put(fieldName, f);
                        continue;
                    }
                    if(fieldType.equals(FieldType.STRING.toString()) && field.hasAttribute("length_type")) {
                        FieldType f = FieldType.STRING;
                        f.setLength(Integer.parseInt(field.getAttribute("count_length")));
                        fieldMap.put(fieldName, f);
                        continue;
                    }
                    fieldMap.put(fieldName, FieldType.parseType(fieldType));
                }
                protocol.addPacketDef(new PacketDefinition(pid, pkName, length, fieldMap, order));
            }

            NodeList constants = doc.getElementsByTagName("const");
            for(int i = 0; i < constants.getLength(); i++) {
                Element element = (Element) constants.item(i);
                String name = element.getAttribute("name");
                ConstantType type = ConstantType.valueOf(element.getAttribute("type"));
                String value = element.getAttribute("value");

                Constant c;
                try {
                    switch (type) {
                        case INTEGER:
                            c = new Constant(name, type, Integer.parseInt(value));
                            break;
                        case DECIMAL:
                            c = new Constant(name, type, Double.parseDouble(value));
                            break;
                        default:
                            c = new Constant(name, type, value);
                    }
                    protocol.addConstant(c);
                } catch (NumberFormatException e) {
                    throw new ParseException("Value of constant \""+name+"\" must match type: \""+type+"\"");
                }
            }
        } catch (Exception e) {
            throw new ParseException(e);
        }
        return protocol;
    }
}
