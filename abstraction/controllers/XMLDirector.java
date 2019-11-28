package converter.abstraction.controllers;

import converter.abstraction.data.XML;
import converter.implementation.xml.JSONParser;
import converter.implementation.xml.XMLBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLDirector {
    private XML xml;
    private String content;
    private XMLBuilder builder = new XMLBuilder();
    private JSONParser parser = new JSONParser();
    private Stack<String> jsonStructure = new Stack<>();

    private void parseElement(List<String> lines) {

        String name;

        for (String line : lines) {
            if (line.trim().matches(".+?(\\s*?\\{)")) {
                name = parser.extractName(line.trim());
                System.out.println("Container start of " + name);
            } else {
                System.out.println("Parsing element " + line.trim());
            }
            System.out.println("------------------");
        }
    }

    public XMLDirector(String content) {
        this.content = content;
    }

    private String insertCharAt(String string, int character) {
        return string.substring(0, character) +
                "\n" + string.substring(character);
    }

    private List<String> beatifyJSON(String json) {
        Pattern pattern = Pattern.compile("(\"|null|\\d)(?=\\s*?})|(,|}(?!,))|\\{(?=\\s*?\")");
        Matcher matcher = pattern.matcher(json);
        List<Integer> newlineChars = new ArrayList<>();

        while (matcher.find()) {
            newlineChars.add(matcher.end());
        }

        for (int i = 0; i < newlineChars.size(); i++) {
            json = insertCharAt(json, newlineChars.get(i) + i);
        }

        List<String> lines = new ArrayList<>(Arrays.asList(json.split("\n")));

        return lines;
    }

    public void startConversion() {
        content = content.replaceAll("\\r\\n|\\r|\\n|\\t", " ");
        content = content.trim().substring(1, content.length() - 1).trim();
        parseElement(beatifyJSON(content));
    }
}
