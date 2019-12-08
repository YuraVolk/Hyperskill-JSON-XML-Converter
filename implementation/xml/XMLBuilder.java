package converter.implementation.xml;

import converter.abstraction.data.JSON;

import java.util.*;
import java.util.logging.Logger;

public class XMLBuilder {
    private JSON json = JSON.root();

    public void createContainer(Stack<String> path, String name) {
        json.addChild(name, true);
    }

    public void goUp() {
        json.goUp();
    }

    public void print() {
        json.getChildren().forEach(JSON::print);
    }

    public void addAttribute(String key, String value) {
        if (value.equals("null")) {
            value = "";
        }

     /*   if (key.startsWith("@")) {
            key = key.substring(1);
        }*/

        if (key.length() == 0 || key.equals("@")) {
            json.stripAttributes();
        } else {
            json.addAttribute(key, value);
        }
    }

    public void setValue(String value) {
        json.setValue(value, true);
    }

    public void createSingleElement(String name, String value, Stack<String> path) {
      /*  if (name.equals(path.peek())) {
            Logger.getAnonymousLogger().config("CXASFD");
        }*/

        if (name.length() == 0 || name.equals("#"))  {
            json.stripAttributes();
        } else {
            json.addChild(name, true);
            json.setValue(value, true);
            json.goUp();
        }
    }

    public void stripAttributes() {
        json.stripAttributes();
    }
}
