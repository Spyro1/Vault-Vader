package backend.fields;

import backend.API;
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
        obj.put(API.TYPE_KEY, "IntField");
        obj.put(API.FIELD_NAME_KEY, super.fieldName);
        obj.put(API.VALUE_KEY, value);
        return obj;
    }

    @Override
    public IntField fromJSON(JSONObject json) {
        fieldName = json.get(API.FIELD_NAME_KEY).toString();
        value = Integer.parseInt(json.get(API.VALUE_KEY).toString());
        return this;
    }
}
