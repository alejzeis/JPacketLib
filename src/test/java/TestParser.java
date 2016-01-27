import io.github.jython234.jpacketlib.DefinitionParser;
import io.github.jython234.jpacketlib.FieldType;
import io.github.jython234.jpacketlib.Protocol;

import java.io.File;

/**
 * Created by atzei on 1/21/2016.
 */
public class TestParser {

    public static void main(String[] args) {
        Protocol p = DefinitionParser.parseDocument(new File("example.npd"));
        System.out.println(p.getName()+", "+p.getVersion());
    }
}
