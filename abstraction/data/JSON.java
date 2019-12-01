package converter.abstraction.data;

import java.util.*;

public class JSON {
    private String name;
    private String value;
    private Map<String, String> attributes = new LinkedHashMap<>();
    private List<JSON> children = new ArrayList<>();
    private static JSON current;
    private JSON parent;

    private JSON(String name) {
        this.name = name;
    }

    public void setName(String name) {
        current.name = name;
    }

    public void setValue(String value) {
        current.value = value;
    }

    public void addAttribute(String key, String value) {
        current.attributes.put(key, value);
    }

    public void addChild(String name) {
        current.children.add(new JSON(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);
    }

    public static JSON root() {
        JSON json = new JSON("JSON_root");
        current = json;
        return json;
    }

    public void print() {
        System.out.println("Element: ");
        System.out.println("Value: " + value);
        System.out.println("Name: " + name);
        System.out.println("Attributes: " + attributes);
        if (parent != null) {
            System.out.println("Parent: " + parent.getName());
        }
        System.out.println();

        children.forEach(JSON::print);
    }

    public String getName() {
        return name;
    }

    public void goUp() {
        if (current.parent != null) {
            current = current.parent;
        }
    }
}
