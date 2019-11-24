package converter.abstraction.controllers;

import converter.abstraction.data.JSON;
import converter.implementation.json.JSONBuilder;
import converter.implementation.json.XMLParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONDirector {
    private JSON json;
    private String content;
    private JSONBuilder builder = new JSONBuilder();
    private XMLParser parser = new XMLParser();

    private void parseElement(String element) {
        if (!parser.checkChildren(element)) {
            String name = parser.extractName(element);
            String attributes = parser.parseAttributes(element);
            Map<String, String> mapAttributes =
                    builder.listOfAttributes(attributes);
            String content = parser.getContent(element, name);

            json = builder.createElement(name,
                    content, mapAttributes);
            System.out.println(json);
        } else {
            System.out.println("nested");
        }
    }

    private String beautifyContent(String content) {
        content = content.replaceAll(">(?![@-~!-;=])", ">\n");
        String[] lines = content.split("\n");
        Pattern pattern = Pattern.compile("</.+?>\n?");
        Matcher matcher;

        List<String> lineList = new ArrayList<>();

        String current;
        String previous;
        for (int i = 0; i < lines.length; i++) {
            matcher = pattern.matcher(lines[i]);
            if (matcher.lookingAt() && i != 0) {
                current = lines[i].substring(2, lines[i].length() - 1);
                previous = lines[i - 1].split(" ")[0].substring(1);
                if (current.equals(previous)) {
                    lineList.set(i - 1, lines[i - 1]
                            .substring(0, lines[i - 1].length() - 2));
                }
            }

            lineList.add(lines[i] + "\n");
        }

        return lineList.toString();
    }

    public JSONDirector(String content) {
        content = content.replaceAll("\\r\\n|\\r|\\n", "");
        System.out.println(beautifyContent(content));
        this.content = content;
    }

    public void startConversion() {
        parseElement(content);
    }
}
