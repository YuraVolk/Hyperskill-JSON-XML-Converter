package converter.abstraction.controllers;

import converter.abstraction.data.JSON;
import converter.implementation.json.JSONBuilder;
import converter.implementation.json.XMLParser;

import java.util.Map;

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
        } //TODO add nesting handling
    }

    public JSONDirector(String content) {
        this.content = content;
    }

    public void startConversion() {
        parseElement(content);
    }
}
