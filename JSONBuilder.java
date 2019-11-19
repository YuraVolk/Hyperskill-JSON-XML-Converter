package converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONBuilder implements Builder {
    private String result;

    @Override
    public void createElement(String element) {
        Pattern pattern = Pattern.compile("(?<=<).+(?=/?>.)");
        Matcher matcher = pattern.matcher(element);
        String name = "";
        String content = "";

        if (matcher.find()) {
            name = matcher.group();
        }

        if (element.matches("<.+>.+</.+>")) {
            String regex = String.format("(?<=<%s>).+(?=<.%s>)",
                    name, name);
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(element);

            if (matcher.find()) {
                content = matcher.group();
            }

            result = String.format("{\"%s\":\"%s\"}",
                    name, content);
        } else {
            pattern = Pattern.compile("(?<=<).+(?=/>)");
            matcher = pattern.matcher(element);

            if (matcher.find()) {
                name = matcher.group();
            }

            result = String.format("{\"%s\":\"null\"}",
                    name);
        }
        print();
    }

    @Override
    public void print() {
        System.out.println(result);
    }
}
