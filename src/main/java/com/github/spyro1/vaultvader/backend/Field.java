package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.JSONObject;

/**
 * The Field class represents a data field with a name, value, and type.
 * It implements the JSONSerializable interface to support JSON serialization
 * and deserialization.
 */
public class Field implements JSONSerializable {

    /** The name of the field. */
    protected String fieldName;

    /** The value of the field. */
    protected String value;

    /** The type of the field, defined by the FieldType enum. */
    protected FieldType type;

    /**
     * Default constructor that initializes a Field with an empty name, empty value,
     * and a default type of FieldType.TEXT.
     */
    public Field() {
        this("", "", FieldType.TEXT);
    }

    /**
     * Constructor that initializes a Field with a specified field name
     * and an empty value. The type defaults to FieldType.TEXT.
     * @param fieldName the name of the field.
     */
    public Field(String fieldName) {
        this(fieldName, "", FieldType.TEXT);
    }

    /**
     * Constructor that initializes a Field with a specified field name and value.
     * The type defaults to FieldType.TEXT.
     * @param fieldName the name of the field.
     * @param value the value of the field.
     */
    public Field(String fieldName, String value) {
        this(fieldName, value, FieldType.TEXT);
    }

    /**
     * Constructor that initializes a Field with a specified field name, value,
     * and type.
     * @param fieldName the name of the field.
     * @param value the value of the field.
     * @param type the type of the field.
     */
    public Field(String fieldName, String value, FieldType type) {
        this.fieldName = fieldName;
        this.value = value;
        this.type = type;
    }

    /**
     * Gets the field name.
     * @return the name of the field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Gets the value of the field.
     * @return the value of the field.
     */
    public String getValue() {
        return value;
    }

    /**
     * Gets the type of the field.
     *
     * @return The field type.
     */
    public FieldType getType() {
        return type;
    }

    /**
     * Sets the value of the field.
     *
     * @param value The new value for the field.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Sets the name of the field.
     *
     * @param fieldName The new field name.
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * Returns a string representation of the field in the format:
     * "fieldName: value [type]".
     *
     * @return The string representation of the field.
     */
    @Override
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