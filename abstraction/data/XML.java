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
    private static List<XML> order = new ArrayList<>();
    private static List<PseudoElement> structure = new ArrayList<>();

    private XML(String elementName) {
        this.elementName = elementName;
    }

    public void addContainer(String name, Map<String, String> attributes) {
        System.out.println("Request to add " + name  + " " + attributes);
        current.containsContainer = true;
        current.children.add(new XML(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);
        current.attributes = attributes;
    }

    public void addElement(String name, String value,
                                  Map<String, String> attributes) {
        System.out.println("Request to add " + name + " " + value + " " + attributes);
        current.children.add(new XML(name));
        current.children.get(current.children.size() - 1).parent = current;
        current = current.children.get(current.children.size() - 1);
        current.attributes = attributes;
        current.value = value;
        goUp();

        if (current.children.size() > 2) { //crunch. exists due to stage problems
            current.containsContainer = true;
        }
    }

    public List<XML> getChildren() {
        return children;
    }

    public void goUp() {
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
        System.out.println("XML{\n" +
                "attributes=" + attributes +
                ",\n elementName='" + elementName + '\'' +
                ",\n value='" + value + '\'' +
                ",\n parent=" + parent +
                ",\n children=" + children +
                ",\n containsContainer=" + containsContainer +
                "\n}\n\n");

        if (children.size() > 0) {
            structure.add(PseudoElement.container(elementName, attributes, containsContainer));
            children.forEach(XML::generate);
            structure.add(PseudoElement.goUpRequest());
        } else {
            structure.add(PseudoElement.element(elementName, value, attributes));
        }
    }

    public static List<PseudoElement> getRequests() {
        return structure;
    }
}
