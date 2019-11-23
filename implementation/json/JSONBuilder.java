package converter.implementation.json;

import converter.abstraction.data.JSON;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONBuilder {
    public Map<String, String> listOfAttributes(String attributes) {
        Pattern pattern = Pattern.compile(".+?\\s*=\\s*\".+?\"");
        Matcher matcher = pattern.matcher(attributes);

        List<String> attrList = new ArrayList<>();
        while (matcher.find()) {
            attrList.add(matcher.group().trim());
        }

        Map<String, String> attrMap = new LinkedHashMap<>();
        String[] attr;

        for (String attribute : attrList) {
            attr = attribute.split("\\s*=\\s*");
            attrMap.put(attr[0], attr[1]);
        }

        return attrMap;
    }

    public JSON createElement(String name, String content,
                              Map<String, String> attributes) {
        JSON json = new JSON();
        json.setName(name);
        json.setValue(content);
        json.setAttributes(attributes);
        return json;
    }

}
