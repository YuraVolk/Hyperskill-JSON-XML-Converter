package converter.implementation.xml;

import converter.PseudoElement;
import converter.abstraction.data.JSON;
import converter.abstraction.data.XML;

import java.util.*;
import java.util.logging.Logger;

public class XMLBuilder {
    private StringBuilder xmlBuilder = new StringBuilder();

    private JSON json;
    public void createContainer(Stack<String> path, String name) {
        json.addChild(name, true);
    }
    public void goUp() {
        json.goUp();
    }
    public void print() {
        if (json.getChildren().size() > 1) {
            json.print();
        } else {
            json.getChildren().forEach(JSON::print);
        }
    }

    public void addAttribute(String key, String value) {
        if (value.equals("null")) {
            value = "";
        }

        if (key.length() == 0 || key.equals("@")) {
            json.stripAttributes();
        } else {
            json.addAttribute(key, value);
        }
    }

    public XMLBuilder(boolean create) {
        if (create) {
            json = JSON.root();
        }
    }

    public List<PseudoElement> result() {
        return JSON.getRequests();
    }

    public void setValue(String value) {
        json.setValue(value, true);
    }

    public void createSingleElement(String name, String value, Stack<String> path) {
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


    public void addContainer(String name, Map<String, String> attributes,
                                                        int depth) {
        xmlBuilder.append("\t".repeat(Math.max(0, depth)));
        xmlBuilder.append("<");
        xmlBuilder.append(name);
        attributes.forEach((key, value) -> {
            xmlBuilder.append(" ");
            xmlBuilder.append(key);
            xmlBuilder.append("=");
            xmlBuilder.append(value);
        });
        xmlBuilder.append(">\n");
    }

    public void createSingleElement(String name, String value,
                                    Map<String, String> attributes,
                                    int depth) {
        xmlBuilder.append("\t".repeat(Math.max(0, depth)));
        xmlBuilder.append("<");
        xmlBuilder.append(name);
        attributes.forEach((key, val) -> {
            xmlBuilder.append(" ");
            xmlBuilder.append(key);
            xmlBuilder.append("=\"");
            xmlBuilder.append(val);
            xmlBuilder.append("\"");
        });
        if (value == null) {
            xmlBuilder.append(" />\n");
        } else if (value.equals("null")) {
            xmlBuilder.append(" />\n");
        } else {
            xmlBuilder.append(">");
            xmlBuilder.append(value);
            xmlBuilder.append("</");
            xmlBuilder.append(name);
            xmlBuilder.append(">\n");
        }

    }

    public void createEnd(String name, int depth) {
        xmlBuilder.append("\t".repeat(Math.max(0, depth - 1)));
        xmlBuilder.append("</");
        xmlBuilder.append(name);
        xmlBuilder.append(">\n");
    }

    public void getResult() {
        System.out.println(xmlBuilder.toString());
    }

}
