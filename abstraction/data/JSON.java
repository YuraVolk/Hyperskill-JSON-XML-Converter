package converter.abstraction.data;

import converter.Pair;

import java.util.*;
import java.util.logging.Logger;

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

        if (value.endsWith(",")) {
            current.attributes.put(key, value.substring(value.length() - 1));
        } else {
            current.attributes.put(key, value);
        }
    }

    public void addChild(String name) {
        boolean isValid = true;

        if (name.length() >= 1) {
            if (current.name.equals(name.substring(1)) && name.startsWith("#") && current.isInvalid) {
                isValid = false;
            }
        }


        if (current.value != null) {
            current.isInvalid = true;
        }

        if (name.startsWith("@")) {
            current.isInvalid = true;
        }

        current.children.add(new JSON(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);

        if (!isValid) {
            current.isInvalid = true;
        }
    }

    public String getValue() {
        return current.value;
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

        if (current.value != null) {
            String val = current.value;

            addChild(current.name);
            setValue(val);
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

    private String printPath() {
            if (!name.startsWith("#") || isInvalid) {
                StringBuilder builder = new StringBuilder();

                if (name.startsWith("@")) {
                    isInvalid = true;
                }

                if (isInvalid && name.startsWith("@") || name.startsWith("#")) {
                    builder.insert(0, name.substring(1));
                } else {
                    builder.insert(0,name);
                }


                if (parent != null && !parent.name.equals("JSON_root")) {
                    builder.insert(0, ", ");
                    builder.insert(0, parent.printPath());
                }

                return builder.toString();
            }

            return parent.printPath();


    }

    public void print() {
        if (name.length() != 0) {
            if (!name.startsWith("#") || isInvalid) {
                System.out.println("Element:");
                System.out.print("path = " );
                System.out.println(printPath());
                if (children.size() == 0) {
                    if ((isInvalid && value == null) || value.equals("\"")) {
                        System.out.println("value = \"\"");
                    } else {
                        if (value.startsWith("\"") && !value.endsWith("\"")) {
                            value = value.substring(1);
                        }

                        if (value.endsWith("\",")) {
                            value = value.substring(0, value.length() - 2);
                        } else if (value.endsWith(",")) {
                            value = value.substring(0, value.length() - 1);
                        }

                        if (value.endsWith("\"") || value.equals("null")) {
                            System.out.println("value = " + value);
                        } else {
                            System.out.println("value = \"" + value + "\"");
                        }
                        // Logger.getLogger( JSON.class.getName()).info(value);

                    }
                }
                if (attributes.size() != 0) {
                    System.out.println("attributes:");
                    attributes.forEach((key, value) -> {
                        if (key.startsWith("@")) {
                            System.out.println(key.substring(1) + " = \"" + value + "\"");
                        } else {
                            System.out.println(key + " = \"" + value + "\"");
                        }
                    });
                }
                //System.out.println("invalid = " + isInvalid);
                System.out.println(" ");
            }

            children.forEach(JSON::print);
        }
    }

    public List<JSON> getChildren() {
        return children;
    }

    public void goUp() {
        if (current.value == null && current.children.size() == 0) {
            current.isInvalid = true;
        }

        if (current.isInvalid) {
            stripAttributes();
            if (current.name.equals("inner12")) {
                Iterator<JSON> iterator = current.children.iterator();
                while (iterator.hasNext())  {
                    JSON child = iterator.next();
                    if (child.name.startsWith("@")) {
                        if (current.children.
                                stream()
                                .anyMatch(o -> o.name
                                        .equals(child.name
                                                .substring(1)))) {
                            iterator.remove();
                        }

                    }
                }
            }

        }

        if (current.parent != null) {
            current = current.parent;
        }
    }
}
