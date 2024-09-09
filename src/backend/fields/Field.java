package backend.fields;

import org.json.simple.JSONObject;

public abstract class Field {
    protected String fieldName;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() { return fieldName; }

    public abstract String toString();

    public abstract JSONObject toJSON();
}
