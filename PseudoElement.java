package converter;

import java.util.List;
import java.util.Map;

public class PseudoElement {
    private boolean isParent;
    private Map<String, String> attributes;
    private List<String> path;
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public void setPath(List<String> path) {
        this.path = path;
    }

    public void print() {

    }
}
