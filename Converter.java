package converter;

import converter.abstraction.controllers.JSONDirector;
import converter.abstraction.controllers.XMLDirector;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

class Converter {
    private String content;

    private void parseXML() {
        JSONDirector director = new JSONDirector(content);
        director.startConversion();
    }

    private void parseJSON() {
        XMLDirector director = new XMLDirector(content);
        director.startConversion();
    }

    void start() {
        try {
            Path path = Paths.get("test.txt");
            content = Files
                    .readString(path, StandardCharsets.UTF_8)
                    .trim();
            if (content.startsWith("<")) {
                parseXML();
            } else {
                parseJSON();
            }
        } catch (IOException e) {
            System.out.println("Error while reading file occurred.");
            System.out.println(e.getMessage());
        }
    }
}
