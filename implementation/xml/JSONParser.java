package converter.implementation.xml;

import converter.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONParser {
    private Pattern pattern;
    private Matcher matcher;

    public Pair<String, String> extractObject(String element) {
        Pair<String, String> pair = new Pair<>();
        pattern = Pattern.compile("(?<=\\{).+(?=})");
        matcher = pattern.matcher(element);

        if (matcher.find()) {
            pair.setSecond(matcher.group());
        } else {
            pair.setSecond("null");
        }

        pattern = Pattern.compile("\".+?\"");
        matcher = pattern.matcher(element);

        if (matcher.find()) {
            pair.setFirst(matcher.group()
                    .substring(1, matcher.group().length() - 1));
        } //TODO add array element support

        return pair;
    }

    public String extractName(String element) {
        pattern = Pattern.compile("(?<=\").+?(?=\")");
        matcher = pattern.matcher(element);

        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public Pair<String, String> extractAttribute(String element) {
        String[] attrArray = element.split("\"\\s*:\\s*\"?");
        Pair<String, String> attr = new Pair<>();
        attr.setFirst(attrArray[0].substring(1));

        String value = attrArray[1];
        if (value.endsWith("\",")) {
            value = value.substring(0, value.length() - 2);
        } else if (value.endsWith("\"")) {
            value = value.substring(0, value.length() - 1);
        }

        attr.setSecond(value);

        return attr;
    }

    public String getValue(String element) {
        String[] values = element.split("\"\\s*:\\s*");
        return values[1];
    }
}
