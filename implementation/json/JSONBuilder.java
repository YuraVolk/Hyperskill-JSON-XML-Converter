package converter.implementation.json;

import converter.abstraction.data.JSON;

import javax.swing.plaf.LabelUI;
import java.time.LocalDateTime;
import java.util.*;
import java.util.logging.Logger;
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
                                    boolean isLastChild, int depth) {

        builder.append("    ".repeat(Math.max(0, depth + 1)));

        builder.append("\"");
        builder.append(name);
        builder.append("\": ");
        if (attributes.size() > 0) {
            builder.append("{\n");
            attributes.forEach((key, val) -> {
                builder.append("    ".repeat(Math.max(0, depth + 2)));
                builder.append("\"@");
                builder.append(key);
                builder.append("\": \"");
                if (val.startsWith("\"")) {
                    builder.append(val.substring(1));
                } else {
                    builder.append(val);
                }
                builder.append(",\n");
            });
            builder.setLength(builder.length() - 2);

            if (!isLastChild) {
                builder.append(",");
            }
            builder.append("\n");

            builder.append("    ".repeat(Math.max(0, depth + 2)));
            builder.append("\"#");
            builder.append(name);
            builder.append("\": ");
            if (value != null) {
                if (value.equals("null")) {
                    builder.append(value);
                    System.out.println("yay");
                } else {
                    if (!value.startsWith("\"") && !value.startsWith("\"")) {
                        value = "\"" + value;
                    }
                    builder.append(value);
                    builder.append("\"");
                }
            } else {

                builder.append(value);
            }

            builder.append("\n");
            builder.append("    ".repeat(Math.max(0, depth + 1)));
            builder.append("}");
            if (!isLastChild) {
                builder.append(",");
            }

        } else {
            if (value != null) {
                if (value.equals("null")) {
                    builder.append(value);
                } else {
                    builder.append("\"");
                    builder.append(value);
                    builder.append("\"");
                }
            } else {
                builder.append("null");
            }

            if (!isLastChild) {
                builder.append(",");
            }
        }
        builder.append("\n");
    }

    public void createContainer(String name, boolean valid,
                                Map<String, String> attributes,
                                int depth) {
        builder.append("    ".repeat(Math.max(0, depth + 1)));

        builder.append("\"");
        builder.append(name);
        System.out.println(name);
        builder.append("\": {\n");

        if (attributes.size() > 0) {
            attributes.forEach((key, val) -> {
                builder.append("    ".repeat(Math.max(0, depth + 1)));
                builder.append("\"@");
                builder.append(key);
                builder.append("\": \"");
                if (val.startsWith("\"")) {
                    builder.append(val.substring(1));
                } else {
                    builder.append(val);
                }
                builder.append(",\n");
            });

            builder.setLength(builder.length() - 2);
            builder.append("\n");
        }

        if (valid) {
            builder.append("    ".repeat(Math.max(0, depth + 1)));
            builder.append("\"#");
            builder.append(name);
            builder.append("\": {\n");
        }
    }

    public void createEnd(boolean isLastChild, int depth) {
        builder.append("    ".repeat(Math.max(0, depth)));

        builder.append("}");
        if (isLastChild) {
            builder.append(",");
        }
        builder.append("\n");
    }

    public void printResults() {
        System.out.println(builder);
    }
}
