package converter.abstraction.controllers;

import converter.Pair;
import converter.abstraction.data.XML;
import converter.implementation.xml.JSONParser;
import converter.implementation.xml.XMLBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDirector {
    private XML xml;
    private String content;
    private XMLBuilder builder = new XMLBuilder();
    private JSONParser parser = new JSONParser();

    private void parseElement(String element) {
       /* System.out.println(parser.hasChildren(element));
        Pair<String, String> response = parser.extractObject(element);
        Pair<String, Map<String, String>> mapPair = builder
                .createAttributes(response.getSecond());
        xml = builder.createElement(mapPair.getFirst(),
                                response.getFirst(),
                                mapPair.getSecond());
        System.out.println(xml);*/
    }

    public XMLDirector(String content) {
        this.content = content;
    }

    private String insertCharAt(String string, int character) {
        return string.substring(0, character) +
                "\n" + string.substring(character);
    }

    private void beatifyJSON(String json) {
        System.out.println(json);
        Pattern pattern = Pattern.compile("(\"|null|\\d)(?=\\s*?})|(,|}(?!,))|\\{(?=\\s*?\")");
        Matcher matcher = pattern.matcher(json);
        List<Integer> newlineChars = new ArrayList<>();

        while (matcher.find()) {
            newlineChars.add(matcher.end());
        }

        for (int i = 0; i < newlineChars.size(); i++) {
            json = insertCharAt(json, newlineChars.get(i) + i);
        }

        System.out.println(json);
    }

    public void startConversion() {
        content = content.replaceAll("\\r\\n|\\r|\\n|\\t", " ");
        content = content.trim();
        beatifyJSON(content);
    }
}
