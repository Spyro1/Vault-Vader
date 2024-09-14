package backend.fields;

import org.json.simple.JSONObject;

public class IntField extends Field {
    protected int value;

    public IntField() {
        super("");
        value = 0;
    }
    public IntField(String fieldName, int value) {
        super(fieldName);
        this.value = value;
    }

    public int getValue() { return this.value; }
    public void setValue(int value) { this.value = value; }

    public String toString() {
        return "{Type: IntField, FieldName: " + super.fieldName + ", Value: " + value + "}";
    }
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", "IntField");
        obj.put("fieldName", super.fieldName);
        obj.put("value", value);
        return obj;
    }

    @Override
    public IntField fromJSON(JSONObject json) {
        fieldName = json.get("fieldName").toString();
        value = Integer.parseInt(json.get("value").toString());
        return this;
    }
}
