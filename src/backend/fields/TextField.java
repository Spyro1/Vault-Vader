package backend.fields;

import backend.API;
import org.json.simple.JSONObject;

public class TextField extends Field {
    protected String text;

    public TextField(){
        super("");
        this.text = "";
    }
    public TextField(String fieldName, String text) {
        super(fieldName);
        this.text = text;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String toString() {
        return "{Type: TextField, FieldName: " + super.fieldName + ", Value: " + text + "}";
    }
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.TYPE_KEY, "TextField");
        obj.put(API.FIELD_NAME_KEY, super.fieldName);
        obj.put(API.VALUE_KEY, text);
        return obj;
    }

    @Override
    public TextField fromJSON(JSONObject json) {
        fieldName = json.get("fieldName").toString();
        text = json.get("value").toString();
        return this;
    }
}
