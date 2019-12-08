package converter;

import java.util.Map;

public class PseudoElement {
    private boolean isParent;
    private Map<String, String> attributes;
    private String name;
    private String value;
    private boolean goUp = false;
    private boolean hasContainers = false;

    public static PseudoElement goUpRequest() {
        PseudoElement element = new PseudoElement();
        element.goUp = true;
        return element;
    }

    public static PseudoElement container(String name,
                                          Map<String, String> attributes,
                                          boolean hasContainers) {
        PseudoElement element = new PseudoElement();
        element.name = name;
        element.attributes = attributes;
        element.isParent = true;
        return element;
    }

    public static PseudoElement element(String name, String value,
                                        Map<String, String> attributes) {
        PseudoElement element = new PseudoElement();
        element.name = name;
        element.value = value;
        element.attributes = attributes;
        element.isParent = false;
        return element;
    }


    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }


    public boolean isParent() {
        return isParent;
    }

    public boolean isGoUp() {
        return goUp;
    }

    public boolean hasContainers() {
        return hasContainers;
    }

    @Override
    public String toString() {
        return "PseudoElement{\n" +
                "isParent=" + isParent +
                ",\n attributes=" + attributes +
                ",\n name=" + name  +
                ",\n value=" + value +
                ",\n goUp=" + goUp +
                ",\n hasContainers=" + hasContainers +
                "\n}\n\n";
    }
}
