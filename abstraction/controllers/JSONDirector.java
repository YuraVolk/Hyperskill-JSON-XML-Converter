package converter.abstraction.controllers;

import converter.abstraction.data.JSON;
import converter.implementation.json.JSONBuilder;
import converter.implementation.json.XMLParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Collections;

public class JSONDirector {
    private JSON json;
    private List<String> content;
    private JSONBuilder builder = new JSONBuilder();
    private XMLParser parser = new XMLParser();
    private Stack<String> executionStack = new Stack<>();

    private void printContainer(String line, String process) {
        Map<String, String> attributes;

        executionStack.push(process);
        System.out.println("Element:");
        System.out.print("path = ");
        System.out.println(executionStack.toString()
                .substring(1, executionStack.toString()
                        .length() - 1));

        attributes = builder.listOfAttributes(parser.parseAttributes(line));

        if (attributes.size() != 0) {
            System.out.println("attributes:");
            attributes.forEach((key, value) -> System.out.println(key + " = " + value));
        }
    }

    private void printElement(String line, String process) {
        Map<String, String> attributes;

        System.out.print("Element:\npath = ");
        executionStack.push(process);
        System.out.println(executionStack.toString()
                .substring(1, executionStack.toString()
                        .length() - 1));
        executionStack.pop();

        String content = parser.getContent(line,
                process);
        if (content != null) {
            System.out.println("value = \"" + content + "\"");
        } else {
            System.out.println("value = null");
        }

        attributes = builder.listOfAttributes(parser.parseAttributes(line));
        if (attributes.size() != 0) {
            System.out.println("attributes:");
            attributes.forEach((key, value) -> System.out.println(key + " = " + value));
        }

    }

    private void parseElement(List<String> elements) {
       String process;

       for (int i = 0; i < elements.size(); i++) {
           process = parser.extractName(elements.get(i));

           if (parser.isParent(elements.get(i))) {
               if (process.startsWith("/")) {
                   executionStack.pop();
                   continue;
               } else {
                   printContainer(elements.get(i), process);
               }
           } else {
                printElement(elements.get(i), process);
           }

           System.out.println(" ");
       }
    }

    private List<String> beautifyContent(String content) {
        content = content.replaceAll(">(?![@-~!-;=])", ">\n");
        String[] lines = content.split("\n");

        for (int i = 0; i < lines.length; i++) {
            lines[i] = lines[i].replaceFirst(" *(?=<)", "") + "\n";
        }

        for (int i = 0; i < lines.length - 1; i++) {
            if (parser.extractName(lines[i]).equals(
                    lines[i+1].trim().substring(2, lines[i+1].trim().length() - 1))) {
                lines[i] = lines[i].substring(0, lines[i].length() - 1);
            }
        }

        StringBuilder builder = new StringBuilder();

        for (String line : lines) {
            builder.append(line);
        }

        List<String> finalList = new ArrayList<>();
        Collections.addAll(finalList, builder.toString().split("\n"));

        return finalList;
    }

    public JSONDirector(String content) {
        content = content.replaceAll("\\r\\n|\\r|\\n|\\t", "");
        this.content = beautifyContent(content);
    }

    public void startConversion() {
        parseElement(content);
    }
}
