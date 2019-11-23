package converter.implementation.json;

import converter.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLParser {
    private Pattern pattern;
    private Matcher matcher;

    public boolean checkChildren(String element) {
        pattern = Pattern.compile("<.+?>");
        matcher = pattern.matcher(element);

        int count = 0;
        while (matcher.find()) {
            count++;
            if (count > 2) {
                return true;
            }
        }

        return false;
    }

    public Pair<Boolean, String> extractName(String element) {
        pattern = Pattern.compile("(?<=<).+?(?=\\s)");
        matcher = pattern.matcher(element);
        if (matcher.find()) {
            String content = matcher.group();

            if (content.endsWith("/")) {
                return new Pair<>(
                        true, content.substring(1));
            } else {
                return new Pair<>(false, content);
            }
        }

        return new Pair<>(false, "null");
    }
}
