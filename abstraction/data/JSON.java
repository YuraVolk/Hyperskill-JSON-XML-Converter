package converter.abstraction.data;

import java.util.List;
import java.util.Map;

public class JSON {
    String name;
    String value;
    Map<String, String> attributes;
    List<JSON> children;

    public void setName(String name) {
        this.name = name;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");
        builder.append("\"");
        builder.append(name);
        builder.append("\" : {\n");
        attributes.forEach((key, value) -> {
            builder.append("\"@");
            builder.append(key);
            builder.append("\" : ");
            builder.append(value);
            builder.append(",\n");
        });
        builder.append("\"#");
        builder.append(name);
        builder.append("\" : \"");
        builder.append(value);
        builder.append("\"\n");
        builder.append("}\n");
        builder.append("}");
        return builder.toString();
    }
}
