package converter.abstraction.controllers;

import converter.Pair;
import converter.PseudoElement;
import converter.abstraction.data.XML;
import converter.implementation.xml.JSONParser;
import converter.implementation.xml.XMLBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XMLDirector {
    private XML xml;
    private String content;
    private XMLBuilder builder = new XMLBuilder(true);
    private JSONParser parser = new JSONParser();
    private Stack<String> jsonStructure = new Stack<>();

    private void printPath() {
        for (String string : jsonStructure) {
            if (!string.startsWith("#")) {
                System.out.print(string);
                if (!string.equals(jsonStructure.peek())) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println();
    }

    private void parseElement(List<String> lines) {

        String name;

        for (String line : lines) {
            if (line.matches(".+?(\\s*?\\{)")) {
                name = parser.extractName(line);
                jsonStructure.add(name);
                builder.createContainer(jsonStructure, name);
            } else if (line.matches("},?")){
                builder.goUp();
                jsonStructure.pop();
            } else {
                if (line.matches("\"@.+")) {
                    Pair<String, String> pair = parser.extractAttribute(line);
                    builder.addAttribute(pair.getFirst(), pair.getSecond());
                } else {
                    if (line.matches("\"#.+?\"\\s*?:\\s*?((\".*?\")|(null)),?")) {
                        if (jsonStructure.peek().equals(parser.extractName(line).substring(1))) {
                            builder.setValue(parser.getValue(line));
                        } else {
                            builder.createSingleElement(
                                    parser.extractName(line).substring(1),
                                    parser.getValue(line),
                                    jsonStructure);
                            builder.stripAttributes();
                        }

                    } else {
                        String[] elem = parser.getElement(line);
                        builder.createSingleElement(elem[0], elem[1], jsonStructure);
                    }
                }
            }
        }

        builder.print();

        List<PseudoElement> requests = builder.result();
        jsonStructure.clear();
        builder = new XMLBuilder(false);

        for (PseudoElement request : requests) {
            if (request.isGoUp()) {
                builder.createEnd(jsonStructure.peek(), jsonStructure.size());
                jsonStructure.pop();
            } else if (request.isParent()) {
                builder.addContainer(request.getName(), request.getAttributes(),
                                                jsonStructure.size());
                jsonStructure.push(request.getName());
            } else {
                builder.createSingleElement(request.getName(),
                        request.getValue(), request.getAttributes(),
                                            jsonStructure.size());
            }
        }

        builder.getResult();
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
        for (int i = 0; i < lines.size(); i++) {
            lines.set(i, lines.get(i).trim());
        }

        return lines;
    }

    public void startConversion() {
        content = content.replaceAll("\\r\\n|\\r|\\n|\\t", " ");
        content = content.trim().substring(1, content.length() - 1).trim();
        parseElement(beatifyJSON(content));
    }
}
