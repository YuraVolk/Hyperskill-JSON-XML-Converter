package converter.abstraction.data;

import converter.PseudoElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XML {
    private Map<String, String> attributes;
    private String elementName;
    private String value;
    private XML parent;
    private List<XML> children = new ArrayList<>();
    private static XML current;
    private boolean containsContainer = false;
    private static List<PseudoElement> structure = new ArrayList<>();
    private boolean isArray = false;
    private boolean isArrayElement = false;
    private boolean isContainer = false;

    private XML(String elementName) {
        this.elementName = elementName;
    }

    public void addContainer(String name, Map<String, String> attributes) {
        current.containsContainer = true;
        current.children.add(new XML(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);
        current.attributes = attributes;
    }

    public void addElement(String name, String value,
                                  Map<String, String> attributes) {

        current.children.add(new XML(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);
        current.attributes = attributes;
        current.value = value;
        if (current.parent != null) {
            current = current.parent;
        }
    }

    public List<XML> getChildren() {
        return children;
    }

    public void goUp() {
        current.isContainer = true;
        if (current.parent != null) {
            current = current.parent;
        }
    }

    public static XML root() {
        XML xml = new XML("XML_root");
        current = xml;
        return xml;
    }

    public void generate() {
        boolean isLast = false;

        if (parent != null) {
            if (elementName.equals(
                    parent.children.get(parent.children.size() - 1)
                            .elementName)) {
                isLast = true;
            }
        }

        if (children.size() > 1) {
            String name = children.get(0).elementName;
            isArray = children.stream().allMatch(e -> e.elementName.equals(name));
            if (isArray) {
                children.forEach(child -> child.isArrayElement = true);
            }
        }

        if (children.size() == 0 && value == null && isContainer
                && attributes.size() == 0) {
            value = "";
        }


        if (children.size() > 0) {
            structure.add(PseudoElement.container(elementName, attributes, containsContainer)
                                        .setChild(isLast).setArray(isArray).setArrayElement(isArrayElement));
            children.forEach(XML::generate);
            structure.add(PseudoElement.goUpRequest().setChild(isLast).setArray(isArray).setArrayElement(isArrayElement));
        } else {
            structure.add(PseudoElement.element(elementName, value, attributes)
                                    .setChild(isLast).setArray(isArray).setArrayElement(parent.isArray));
        }
    }

    public static List<PseudoElement> getRequests() {
        return structure;
    }
}
