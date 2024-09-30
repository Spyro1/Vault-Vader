package com.github.spyro1.vaultvader.backend.fields;

import com.github.spyro1.vaultvader.backend.API;
import com.github.spyro1.vaultvader.backend.JSONSerializable;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.Serializable;

public class Field implements JSONSerializable {

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
        return fieldName + ": " + text + " [" + type + "]";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.TYPE_KEY, type.toString());
        obj.put(API.FIELD_NAME_KEY, fieldName);
        obj.put(API.VALUE_KEY, text);
        return obj;
    }

    @Override
    public Field fromJSON(JSONObject json) {
        type = FieldType.fromString(json.get(API.TYPE_KEY).toString());
        fieldName = json.get(API.FIELD_NAME_KEY).toString();
        text = json.get(API.VALUE_KEY).toString();
        return this;
    }
}