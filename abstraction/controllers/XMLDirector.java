package converter.abstraction.controllers;

import converter.Pair;
import converter.abstraction.data.XML;
import converter.implementation.xml.JSONParser;
import converter.implementation.xml.XMLBuilder;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Element Start ->
 * 	mark element as container
 * 	parse all attributes -> {
 * 		apply correction rules()
 * 		if (passed) -> {
 * 			continue
 *                } else {
 * 			go back to start of element
 * 			start parsing as container, ignoring @ and #
 *        }* 	}
 * 	if (# symbol found) -> {
 * 		iscontainer ?> start "Element Start" ?< mark element as container* 	} else {
 * 		stop parsing. no print* 	}
 *
 * 	if (found end and stack pull is element) {
 * 		continue with next elements* 	}
 * }
 */
public class XMLDirector {
    private XML xml;
    private String content;
    private XMLBuilder builder = new XMLBuilder();
    private JSONParser parser = new JSONParser();
    private Stack<String> jsonStructure = new Stack<>();

    private void parseElement(List<String> lines) {

        String name;

        for (String line : lines) {
            if (line.matches(".+?(\\s*?\\{)")) {
                name = parser.extractName(line);
                System.out.println("Container start of " + name);
                jsonStructure.add(name);
                builder.createContainer(jsonStructure, name);
            } else if (line.matches("},?")){
                System.out.println("Container end of " + jsonStructure.peek());
                jsonStructure.pop();
            } else {
                if (line.matches("\"@.+")) {
                    System.out.println(line);
                    System.out.println("Parsing attribute " + parser.extractAttribute(line));
                } else {
                    System.out.println("Parsing element " + line);
                }
            }
            System.out.println(jsonStructure);
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
