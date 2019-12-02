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
        current.print();
    }

    public static JSON root() {
        JSON json = new JSON("JSON_root");
        current = json;
        return json;
    }

    public String printPath() {
        if (!name.startsWith("#")) {
            StringBuilder builder = new StringBuilder();

            builder.insert(0,name);
            if (parent != null && !parent.name.equals("JSON_root")) {
                builder.insert(0, ", ");
                builder.insert(0, parent.printPath());
            }

            return builder.toString();
        }

        return parent.printPath();
    }

    public void print() {
        if (!name.startsWith("#")) {
            System.out.println("Element:");
            System.out.print("path = " );
            System.out.println(printPath());
            if (children.size() == 0) {
                System.out.println("value = " + value);
            }
            if (attributes.size() != 0) {
                System.out.println("attributes:");
                attributes.forEach((key, value) -> System.out.println(key + " = \"" + value + "\""));
            }
            System.out.println(" ");
        }

        children.forEach(JSON::print);
    }

    public List<JSON> getChildren() {
        return children;
    }

    public void goUp() {
        if (current.parent != null) {
            current = current.parent;
        }
    }
}
