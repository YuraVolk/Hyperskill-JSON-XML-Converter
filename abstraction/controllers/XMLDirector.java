package converter.abstraction.controllers;

import converter.abstraction.data.XML;
import converter.implementation.xml.JSONParser;
import converter.implementation.xml.XMLBuilder;

public class XMLDirector {
    private XML xml;
    private String content;
    private XMLBuilder builder = new XMLBuilder();
    private JSONParser parser = new JSONParser();

    private void parseElement(String element) {
        System.out.println(parser.hasChildren(element));
        System.out.println(parser.extractObject(element));
    }

    public XMLDirector(String content) {
        this.content = content;
    }

    public void startConversion() {
        content = content.replaceAll("\\r\\n|\\r|\\n", " ");
        content = content.trim()
                .substring(1, content.length() - 1)
                .trim();
        parseElement(content);
    }
}
