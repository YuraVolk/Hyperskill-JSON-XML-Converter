package converter.implementation.xml;

import converter.abstraction.data.JSON;

import java.util.*;

public class XMLBuilder {
    private JSON json = JSON.root();

    public void createContainer(Stack<String> path, String name) {
        json.addChild(name);
    }

    public void goUp() {
        json.goUp();
    }

    public void print() {
        json.print();
    }

    public void addAttribute(String key, String value) {
        json.addAttribute(key, value);
    }

    public void setValue(String value) {
        json.setValue(value);
    }

    public void createSingleElement(String name, String value, Stack<String> path) {
        json.addChild(name);
        json.setValue(value);
        json.goUp();
    }
}
