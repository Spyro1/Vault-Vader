package backend.fields;

import org.json.simple.JSONObject;

public abstract class Field {
    // Constants
    public static final int INTFIELD = 0;
    public static final int TEXTFIELD = 1;
    public static final int PASSFIELD = 2;

    protected String fieldName;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() { return fieldName; }

    public abstract String toString();

    public abstract JSONObject toJSON();
}
