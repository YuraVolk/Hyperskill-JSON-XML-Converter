package converter.abstraction.data;

import java.util.List;
import java.util.Map;

public class XML {
    Map<String, String> attributes;
    String elementName;
    String value;
    boolean isContainer;
    List<XML> children;
}
