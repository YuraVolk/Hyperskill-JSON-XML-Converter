package converter.abstraction.data;

import java.util.List;
import java.util.Map;

public class XML {
    private Map<String, String> attributes;
    private String elementName;
    private String value;
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
        builder.append("<");
        builder.append(elementName);
        attributes.forEach((key, value) -> {
            builder.append(" ");
            builder.append(key);
            builder.append("=");
            if (value.startsWith("\"")) {
                builder.append(value);
            } else {
                builder.append("\"");
                builder.append(value);
                builder.append("\"");
            }
        });

        if (value == null) {
            builder.append(" />");
        } else {
            builder.append(">");
            builder.append(value);
            builder.append("</");
            builder.append(elementName);
            builder.append(">");
        }

        return builder.toString();
    }
}
