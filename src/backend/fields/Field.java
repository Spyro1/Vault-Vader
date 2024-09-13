package backend.fields;

import backend.JSONSerializable;
import org.json.simple.JSONObject;

import java.io.Serializable;

public abstract class Field implements JSONSerializable {
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
}
