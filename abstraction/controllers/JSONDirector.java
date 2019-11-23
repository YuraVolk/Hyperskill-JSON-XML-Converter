package converter.abstraction.controllers;

import converter.abstraction.data.JSON;
import converter.implementation.json.JSONBuilder;

public class JSONDirector {
    private JSON json;
    private String content;
    private JSONBuilder builder = new JSONBuilder();

    public JSONDirector(String content) {
        this.content = content;
    }

    public void startConversion() {

    }
}
