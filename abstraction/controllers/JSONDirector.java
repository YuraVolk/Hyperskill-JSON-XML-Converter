package converter.abstraction.controllers;

import converter.abstraction.data.JSON;
import converter.implementation.json.JSONBuilder;
import converter.implementation.json.XMLParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONDirector {
    private JSON json;
    private List<String> content;
    private JSONBuilder builder = new JSONBuilder();
    private XMLParser parser = new XMLParser();
    private Stack<String> executionStack = new Stack<>();

    private void parseElement(List<String> elements) {
       /* if (!parser.checkChildren(element)) {
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
        }*/


       System.out.println(elements);
       String process;
       String currentName;

       for (int i = 0; i < elements.size(); i++) {
           if (parser.isParent(elements.get(i))) {
               process = parser.extractName(elements.get(i));
               if (process.startsWith("/")) {
                   System.out.print("Container end of: " + elements.get(i));
                   executionStack.pop();
               } else {
                   System.out.print("Container start of:  " + elements.get(i));
                   System.out.println("Attributes: " +
                           builder.listOfAttributes(
                                   parser.parseAttributes(elements.get(i))
                           ));
                   currentName = parser.extractName(elements.get(i));
                   System.out.println("Name: " + currentName);
                   executionStack.push(process);
               }
           } else {
               System.out.print("Parsing element: " + elements.get(i));
               System.out.println("Attributes: " +
                       builder.listOfAttributes(
                               parser.parseAttributes(elements.get(i))
                       ));
               currentName = parser.extractName(elements.get(i));
               System.out.println("Name: " + currentName);
               System.out.println("Value: " + parser.getContent(elements.get(i),
                       currentName));
           }
           System.out.println("-----------------------------------------------");
       }
       System.out.println(executionStack);

    }

    private List<String> beautifyContent(String content) {
        content = content.replaceAll(">(?![@-~!-;=])", ">\n");
        String[] lines = content.split("\n");

        List<String> lineList = new ArrayList<>();

        for (int i = 0; i < lines.length; i++) {
            lineList.add(lines[i].replaceFirst(" *(?=<)", "") + "\n");
        }

        return lineList;
    }

    public JSONDirector(String content) {
        content = content.replaceAll("\\r\\n|\\r|\\n", "");
        this.content = beautifyContent(content);
    }

    public void startConversion() {
        parseElement(content);
    }
}
