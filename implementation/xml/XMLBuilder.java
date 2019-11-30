package converter.implementation.xml;

import converter.abstraction.data.JSON;

import java.util.*;

public class XMLBuilder {
    private JSON json = JSON.root();

    public void createContainer(Stack<String> path, String name) {
     /*   JSON current = json;

        for (String string : path) {
            if (!string.startsWith("#")) {
                current = current.containsChild(string);
            }
        }

        current.addChild(name);
        json = current;
        json.print();*/
    }
}
