package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.JSONObject;

public class Field implements JSONSerializable {

    protected String fieldName;
    protected String value;
    protected FieldType type;

    public Field() {
        this("","", FieldType.TEXT);
    }
    public Field(String fieldName) {
        this(fieldName, "", FieldType.TEXT);
    }
    public Field(String fieldName, String value) {
        this(fieldName, value, FieldType.TEXT);
    }
    public Field(String fieldName, String value, FieldType type) {
        this.fieldName = fieldName;
        this.value = value;
        this.type = type;
    }

    public String getFieldName() { return fieldName; }
    public String getValue() { return value; }
    public FieldType getType() { return type; }

    public String toString() {
        return fieldName + ": " + value + " [" + type + "]";
    }

    /**
     * @JSONkeys "fieldName", "type", "vale"
     */
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.TYPE_KEY, type.toString());
        obj.put(API.FIELD_NAME_KEY, fieldName);
        obj.put(API.VALUE_KEY, value);
        return obj;
    }

    /**
     * @JSONkeys "fieldName", "type", "vale"
     */
    @Override
    public Field fromJSON(JSONObject json) {
        if (json.containsKey(API.TYPE_KEY))
            type = FieldType.fromString(json.get(API.TYPE_KEY).toString());
        if (json.containsKey(API.FIELD_NAME_KEY))
            fieldName = json.get(API.FIELD_NAME_KEY).toString();
        if (json.containsKey(API.VALUE_KEY))
            value = json.get(API.VALUE_KEY).toString();
        return this;
    }
}