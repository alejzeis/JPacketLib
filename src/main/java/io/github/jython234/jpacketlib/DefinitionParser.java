package io.github.jython234.jpacketlib;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
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

            NodeList packets = doc.getElementsByTagName("packet");
            for(int i = 0; i < packets.getLength(); i++) {
                Element element = (Element) packets.item(i);
                element.normalize();
                String pkName = element.getAttribute("name");
                int pid = Integer.parseInt(element.getElementsByTagName("pid").item(0).getTextContent(), 16);

                Map<String, FieldType> fieldMap = new HashMap<>();

                NodeList fields = element.getElementsByTagName("field");
                for(int i2 = 0; i2 < fields.getLength(); i2++) {
                    Element field = (Element) fields.item(i2);
                    field.normalize();
                    String fieldName = field.getAttribute("name");
                    String fieldType = field.getAttribute("type");
                    if(fieldType.equals(FieldType.BYTES.toString())) {
                        if(!field.hasAttribute("length")) throw new ParseException("Length not specified for fieldType \"bytes\"");
                        FieldType f = FieldType.BYTES;
                        f.setLength(Integer.parseInt(field.getAttribute("length")));
                        fieldMap.put(fieldName, f);
                    }
                    fieldMap.put(fieldName, FieldType.parseType(fieldType));
                }
                protocol.addPacketDef(new PacketDefinition(pid, pkName, fieldMap));
            }
        } catch (Exception e) {
            throw new ParseException(e);
        }
        return protocol;
    }
}
