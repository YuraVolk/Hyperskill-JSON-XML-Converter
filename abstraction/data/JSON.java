package converter.abstraction.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JSON {
    private String name;
    private String value;
    private Map<String, String> attributes;
    private List<JSON> children = new ArrayList<>();

    private JSON(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void addChild(String name) {
        children.add(new JSON(name));
    }

    public static JSON root() {
        return new JSON("JSON_root");
    }

    public void print() {
        System.out.println("Element: ");
        System.out.println("Value: " + value);
        System.out.println("Name: " + name);
        System.out.println("Attributes: " + attributes);
        children.forEach(JSON::print);
    }

    public JSON containsChild(String name) {
        for (JSON child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }

        return this;
    }

    public String getName() {
        return name;
    }
}
