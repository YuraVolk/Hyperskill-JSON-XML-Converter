package converter.abstraction.data;

import java.util.List;
import java.util.Map;

public class XML {
    private Map<String, String> attributes;
    private String elementName;
    private String value = null;
    private List<XML> children;

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setElementName(String elementName) {
        this.elementName = elementName;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        System.out.println(value);
        System.out.println(elementName);
        System.out.println(attributes);
        return "";
    }
}
