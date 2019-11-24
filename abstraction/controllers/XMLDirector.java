package converter.abstraction.controllers;

import converter.Pair;
import converter.abstraction.data.XML;
import converter.implementation.xml.JSONParser;
import converter.implementation.xml.XMLBuilder;

import java.util.Map;

public class XMLDirector {
    private XML xml;
    private String content;
    private XMLBuilder builder = new XMLBuilder();
    private JSONParser parser = new JSONParser();

    private void parseElement(String element) {
        System.out.println(parser.hasChildren(element));
        Pair<String, String> response = parser.extractObject(element);
        Pair<String, Map<String, String>> mapPair = builder
                .createAttributes(response.getSecond());
        xml = builder.createElement(mapPair.getFirst(),
                                response.getFirst(),
                                mapPair.getSecond());
        System.out.println(xml);
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
