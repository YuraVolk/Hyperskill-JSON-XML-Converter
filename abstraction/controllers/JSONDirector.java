package converter.abstraction.controllers;

import converter.abstraction.data.JSON;
import converter.implementation.json.JSONBuilder;
import converter.implementation.json.XMLParser;

public class JSONDirector {
    private JSON json;
    private String content;
    private JSONBuilder builder = new JSONBuilder();
    private XMLParser parser = new XMLParser();

    private void parseElement(String element) {
        if (!parser.checkChildren(element)) {
            System.out.println(parser.extractName(element));
            String attributes = parser.parseAttributes(element);
            builder.listOfAttributes(attributes);
        } //TODO add nesting handling
    }

    public JSONDirector(String content) {
        this.content = content;
    }

    public void startConversion() {
        parseElement(content);
    }
}
