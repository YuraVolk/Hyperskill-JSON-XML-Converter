package converter.implementation.xml;

import converter.Pair;
import converter.abstraction.data.XML;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

public class XMLBuilder {

    public Pair<String, Map<String, String>>
                        createAttributes(String element) {
        Map<String, String> attrMap = new HashMap<>();
        List<String> attrList = new ArrayList<>();

        String[] arr = element.split(",");
        Collections.addAll(attrList, arr);

        Pair<String, String> name = new Pair<>();

        String[] attr;
        for (String attribute : attrList) {
            attr = attribute.trim().split("\\s*:\\s*");
            if (attr[0].startsWith("\"#")) {
                name = new Pair<>(attr[0], attr[1]);
            } else {
                attrMap.put(attr[0]
                        .substring(2, attr[0].length() - 1),
                        attr[1]);
            }
        }

        return new Pair<>(name.getSecond(), attrMap);
    }

    public XML createElement(String value, String tag, Map<String, String> attr) {
        XML xml = new XML();
        xml.setAttributes(attr);

        if (!value.equals("null")) {
            xml.setValue(value.substring(1, value.length() - 1));
        }

        xml.setElementName(tag);
        return xml;
    }
}
