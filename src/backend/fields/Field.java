package backend.fields;

import backend.API;
import backend.JSONSerializable;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.Serializable;

public class Field implements JSONSerializable {
    public static enum FieldType {
        TEXT,
        INT,
        PASS,
        CATEGORY
    }
    // Constants
//    public static final int TEXTFIELD = 0;
//    public static final int INTFIELD = 1;
//    public static final int PASSFIELD = 2;
//    public static final int CATEGORYFIELD = 3;

    protected String fieldName;
    protected String text;
    protected FieldType type;

    public Field() {
        this("","", FieldType.TEXT);
    }
    public Field(String fieldName) {
        this(fieldName, "", FieldType.TEXT);
    }
    public Field(String fieldName, String text) {
        this(fieldName, text, FieldType.TEXT);
    }
    public Field(String fieldName, String text, FieldType type) {
        this.fieldName = fieldName;
        this.text = text;
        this.type = type;
    }

    public String getFieldName() { return fieldName; }
    public String getText() { return text; }
    public FieldType getType() { return type; }

    public String toString() {
        return fieldName + " " + text + " " + type;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.TYPE_KEY, type);
        obj.put(API.FIELD_NAME_KEY, fieldName);
        obj.put(API.VALUE_KEY, text);
        return obj;
    }

    @Override
    public Field fromJSON(JSONObject json) {
        type = (FieldType) (json.get(API.TYPE_KEY));
        fieldName = json.get(API.FIELD_NAME_KEY).toString();
        text = json.get(API.VALUE_KEY).toString();
        return this;
    }
}