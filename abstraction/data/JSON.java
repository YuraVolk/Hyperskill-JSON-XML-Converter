package converter.abstraction.data;

import java.util.*;

public class JSON {
    private String name;
    private String value;
    private Map<String, String> attributes = new LinkedHashMap<>();
    private List<JSON> children = new ArrayList<>();
    private static JSON current;
    private JSON parent;
    private boolean isInvalid = false;


    private JSON(String name) {
        this.name = name;
    }

    public void setValue(String value) {
        if (current.isInvalid) {
            current.addChild(current.name);
            current.setValue(value);
            current.goUp();
        } else {
            current.value = value;
        }
    }

    public void addAttribute(String key, String value) {
        current.attributes.put(key, value);
    }

    public void addChild(String name) {
        if (name.endsWith(",")) {
            name = name.substring(0, name.length() - 1);
        }

        current.children.add(new JSON(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);
    }

    public String getName() {
        return current.name;
    }

    public void stripAttributes() {
        Map<String, String> map = current.attributes;

        map.forEach((key, value) -> {
            addChild(key);
            setValue(value);
            goUp();
        });

        if (value != null) {
            addChild(name);
            setValue(value);
            goUp();
        }

        current.isInvalid = true;
        current.attributes.clear();
    }

    public static JSON root() {
        JSON json = new JSON("JSON_root");
        current = json;
        return json;
    }

    public String printPath() {

            StringBuilder builder = new StringBuilder();

            builder.insert(0,name);
            if (parent != null && !parent.name.equals("JSON_root")) {
                builder.insert(0, ", ");
                builder.insert(0, parent.printPath());
            }

            return builder.toString();


     //   return parent.printPath();
    }

    public void print() {
        if (name.length() != 0) {
           // if (!name.startsWith("#") && isInvalid == false) {
                System.out.println("Element:");
                System.out.print("path = " );
                System.out.println(printPath());
                if (children.size() == 0) {
                    if (isInvalid && children.size() == 0 && value == null) {
                        System.out.println("value = \"\"");
                    } else {
                        System.out.println("value = " + value);
                    }
                }
                if (attributes.size() != 0) {
                    System.out.println("attributes:");
                    attributes.forEach((key, value) -> System.out.println(key + " = \"" + value + "\""));
                }
                System.out.println("invalid = " + isInvalid);
                System.out.println(" ");
          //  }

            children.forEach(JSON::print);
        }
    }

    public List<JSON> getChildren() {
        return children;
    }

    public void goUp() {
        System.out.println("******************");
        System.out.println(current.value);
        System.out.println(current.value);
        if (current.value == null && children.size() == 0 && attributes.size() != 0) {
            current.isInvalid = true;
        }

        if (current.isInvalid) {
            stripAttributes();
        }

        if (current.parent != null) {
            current = current.parent;
        }
    }
}
