package backend.fields;

import org.json.simple.JSONObject;

public class TextField extends Field {
    protected String text;

    public TextField(String fieldName, String text) {
        super(fieldName);
        this.text = text;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String toString() {
        return "{Type: TextField, FieldName: " + super.fieldName + ", Value: " + text + "}";
    }
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", "TextField");
        obj.put("fieldName", super.fieldName);
        obj.put("value", text);
        return obj;
    }
}
