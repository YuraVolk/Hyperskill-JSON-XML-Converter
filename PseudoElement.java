package converter;

import java.util.Map;

public class PseudoElement {
    private boolean isParent;
    private Map<String, String> attributes;
    private String name;
    private String value;
    private boolean goUp = false;
    private boolean isLast = false;
    private boolean isArray = false;
    private boolean isArrayElement = false;

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
        if (value != null) {
            value = value.replaceAll(",", "");
        }
        element.name = name;
        element.value = value;
        element.attributes = attributes;
        element.isParent = false;

        return element;
    }

    public PseudoElement setChild(boolean isLast) {
        this.isLast = isLast;
        return this;
    }

    public boolean isLast() {
        return isLast;
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

    public PseudoElement setArray(boolean isArray) {
        this.isArray = isArray;
        return this;
    }

    public PseudoElement setArrayElement(boolean isArrayElement) {
        this.isArrayElement = isArrayElement;
        return this;
    }

    public boolean isArray() {
        return isArray;
    }

    public boolean isArrayElement() {
        return isArrayElement;
    }

    @Override
    public String toString() {
        return "PseudoElement{" +
                "isParent=" + isParent +
                ", attributes=" + attributes +
                ", name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", goUp=" + goUp +
                ", isLast=" + isLast +
                ", isArray=" + isArray +
                ", isArrayElement=" + isArrayElement +
                '}';
    }
}
