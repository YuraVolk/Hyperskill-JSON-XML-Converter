package converter.abstraction.data;

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
    private boolean stripped = false;
    private Set<String> additionHistory = new LinkedHashSet<>();

    private JSON(String name) {
        this.name = name;
    }

    public void setValue(String value, boolean add) {

        if (current.isInvalid) {
            current.addChild(current.name, true);
            current.setValue(value, false);
            current.goUp();
        } else {
            current.value = value;
        }

        if (add) {
            current.additionHistory.add("#" + current.name);
        }
    }

    public void addAttribute(String key, String value) {
        current.additionHistory.add(key);
        if (value.endsWith(",")) {
            current.attributes.put(key, value.substring(value.length() - 1));
        } else {
            current.attributes.put(key, value);
        }
    }

    public void addChild(String name, boolean toAdd) {
        boolean isValid = true;

        if (toAdd) {
            current.additionHistory.add("#" + current.name);
        }

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

    public void stripAttributes() {
        Map<String, String> map = current.attributes;

        map.forEach((key, value) -> {
            addChild(key, false);
            setValue(value, false);
            goUp();
            current.stripped = true;
        });

        if (current.value != null) {
            String val = current.value;
            addChild("#" + current.name, false);
            setValue(val, false);
            goUp();
        }

        if (current.children.size() > 0) {
            if (current.children.get(0).name.equals(current.name)
                    && current.name.startsWith("#")
                    && current.value == null
                    && current.children.get(0).value != null) {
                current.value = current.children.get(0).value;
                current.children.clear();
            }
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
                Iterator<JSON> iterator = current.children.iterator();
                while (iterator.hasNext())  {
                    JSON child = iterator.next();
                    if (child.name.startsWith("@")
                            || child.name.startsWith("#")) {
                        if (current.children.
                                stream()
                                .anyMatch(o -> o.name
                                        .equals(child.name
                                                .substring(1)))) {
                            iterator.remove();
                        }

                    }
                }

                if (current.stripped && current.children.size() > 1 && current.additionHistory.size() > 0) {
                    int currentIndex = 0;
                    for (String line : current.additionHistory) {
                            JSON json =  current.children.stream()
                                    .filter(customer -> line.equals(customer.name))
                                    .findAny()
                                    .orElse(null);
                            if (json != null) {
                                current.children.remove(json);
                                current.children.add(currentIndex, json);
                                currentIndex++;
                            }
                    }
                }

        }

        if (current.parent != null) {
            current = current.parent;
        }
    }
}
