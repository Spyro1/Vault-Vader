package com.github.spyro1.vaultvader.backend;

import java.util.ArrayList;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.*;

/**
 * The Item class represents an item with a unique ID, icon, title, category, and a list of fields. It implements the
 * JSONSerializable interface to support conversion to and from JSON format.
 */
public class Item implements JSONSerializable {
    
    /** A static counter to generate unique IDs for each Item instance. */
    private static int idCounter = 0;
    
    /** The unique identifier for this Item. */
    private final int ID = idCounter++;
    
    /** The icon associated with the Item. */
    private String icon;
    
    /** The title of the Item. */
    private String title;
    
    /** The category field for the Item. */
    private Field category;
    
    /** A list of fields associated with the Item. */
    private ArrayList<Field> fields = new ArrayList<>();
    
    /**
     * Default constructor that initializes the Item with default values.
     */
    public Item() {
        setupItem();
    }
    
    /**
     * Initializes the Item with default values.
     */
    private void setupItem() {
        title = null;
        category = new Field("Kategória", "", FieldType.CATEGORY);
        icon = "picture.png";
        fields.clear();
        fields.add(new Field("Felhasználónév", "", FieldType.TEXT));
        fields.add(new Field("Jelszó", "", FieldType.PASS));
    }
    
    /**
     * Gets the unique ID of the Item.
     * @return The unique ID of the Item.
     */
    public int getID() {
        return ID;
    }
    
    /**
     * Gets the icon of the Item.
     * @return The icon of the Item.
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * Gets the title of the Item.
     * @return The title of the Item.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Gets the category of the Item.
     * @return The category of the Item.
     */
    public Field getCategory() {
        return category;
    }
    
    /**
     * Gets the list of fields associated with the Item.
     * @return The list of fields.
     */
    public ArrayList<Field> getFields() {
        return fields;
    }
    
    /**
     * Sets the icon for the Item.
     * @param icon The icon to set.
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    /**
     * Sets the title for the Item.
     * @param title The title to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Sets the category for the Item.
     * @param category The category to set.
     */
    public void setCategory(Field category) {
        this.category = category;
    }
    
    /**
     * Sets the list of fields for the Item.
     * @param fields The list of fields to set.
     */
    public void setFields(ArrayList<Field> fields) {
        this.fields = fields;
    }
    
    /**
     * Adds a new field to the Item.
     * @param field The field to add.
     * @return The current Item instance (for chaining).
     */
    public Item addField(Field field) {
        fields.add(field);
        return this;
    }
    
    /**
     * Returns a string representation of the Item in the format: "title [category]".
     * @return The string representation of the Item.
     */
    @Override
    public String toString() {
        return String.format("%s [%s]", title, category.getValue());
    }
    
    
    /**
     * @JSONkeys "icon", "title", "category", "fields"
     */
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.ICON_KEY, icon);
        obj.put(API.TITLE_KEY, title);
        obj.put(API.CATEGORY_KEY, category.getValue());
        JSONArray fieldsArray = new JSONArray();
        for (Field field : fields) {
            fieldsArray.add(field.toJSON());
        }
        obj.put(API.FIELDS_KEY, fieldsArray);
        return obj;
    }
    
    /**
     * @JSONkeys "icon", "title", "category", "fields"
     */
    @Override
    public Item fromJSON(JSONObject json) throws Exception {
        if (json != null && json.containsKey(API.ICON_KEY) && json.containsKey(API.TITLE_KEY) && json.containsKey(API.CATEGORY_KEY) && json.containsKey(API.FIELDS_KEY)) {
            icon = json.get(API.ICON_KEY).toString();
            title = json.get(API.TITLE_KEY).toString();
            category.setValue(json.get(API.CATEGORY_KEY).toString());
            fields.clear();
            JSONArray fieldsArray = (JSONArray) json.get(API.FIELDS_KEY);
            for (Object fieldObj : fieldsArray) {
                JSONObject jsonFieldData = (JSONObject) fieldObj;
                fields.add(new Field().fromJSON(jsonFieldData));
            }
            return this;
        } else {
            throw new Exception("Not enough key-value pairs give in " + getClass().toString() + ".fromJSON() argument.");
        }
    }
    
    /**
     * Resets the Item to its default state.
     */
    public void reset() {
        setupItem();
    }
}
