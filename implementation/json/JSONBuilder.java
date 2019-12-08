package converter.implementation.json;

import converter.abstraction.data.JSON;

import javax.swing.plaf.LabelUI;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONBuilder {
    private StringBuilder builder = new StringBuilder();

    public Map<String, String> listOfAttributes(String attributes) {
        Pattern pattern = Pattern.compile(".+?\\s*=\\s*\".+?\"");
        Matcher matcher = pattern.matcher(attributes);

        List<String> attrList = new ArrayList<>();
        while (matcher.find()) {
            attrList.add(matcher.group().trim());
        }

        Map<String, String> attrMap = new LinkedHashMap<>();
        String[] attr;

        for (String attribute : attrList) {
            attr = attribute.split("\\s*=\\s*");
            attrMap.put(attr[0], attr[1]);
        }

        return attrMap;
    }

    public void start() {
        builder.append("{\n");
    }

    public void end() {
        builder.append("}");
    }

    public void createSingleElement(String name, String value,
                                    Map<String, String> attributes,
                                    boolean isLastChild) {
        builder.append("\"");
        builder.append(name);
        builder.append("\": ");
        if (attributes.size() > 0) {
            builder.append("{\n");
            attributes.forEach((key, val) -> {
                builder.append("\"@");
                builder.append(key);
                builder.append("\": \"");
                builder.append(val);
                builder.append(",\n");
            });
            builder.setLength(builder.length() - 2);
            builder.append("\n}");
            if (!isLastChild) {
                builder.append(",");
            }
        } else {
            builder.append("\"");
            builder.append(value);
            builder.append("\"");
            if (!isLastChild) {
                builder.append(",");
            }
        }
        builder.append("\n");
    }

    public void createContainer(String name, boolean valid,
                                Map<String, String> attributes) {
        builder.append("\"");
        builder.append(name);
        builder.append("\": {\n");

        attributes.forEach((key, val) -> {
            builder.append("\"@");
            builder.append(key);
            builder.append("\": \"");
            builder.append(val);
            builder.append(",\n");
        });

        builder.setLength(builder.length() - 2);
        builder.append("\n}");

        if (valid) {
            builder.append("\"#");
            builder.append(name);
            builder.append("\": {\n");
        }
    }

    public void createEnd(boolean isLastChild) {
        builder.append("}");
        if (isLastChild) {
            builder.append(",");
        }
        builder.append("\n");
    }
}
