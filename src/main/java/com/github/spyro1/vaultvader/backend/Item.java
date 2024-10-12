package com.github.spyro1.vaultvader.backend;

import java.util.ArrayList;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.*;

public class Item implements JSONSerializable {
    private static int idCounter = 0;

    private final int ID = idCounter++;
    private String icon;
    private String title;
    private Field category;
    private ArrayList<Field> fields = new ArrayList<>();

    public Item() {
        setupItem();
    }

    public Item(int categoryIdx) {
        setupItem();
    }

    private void setupItem(){
        title = null;
        category = new Field("Kategória", "", FieldType.CATEGORY);
        icon = "picture.png";
        fields.clear();
        fields.add(new Field("Felhasználónév", "", FieldType.TEXT));
        fields.add(new Field("Jelszó", "", FieldType.PASS));
    }

    public int getID() { return ID;}
    public String getIcon() { return icon; }
    public String getTitle() { return title; }
    public Field getCategory() { return category; }
    public ArrayList<Field> getFields() { return fields; }

    public void setIcon(String icon) { this.icon = icon; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(Field category) { this.category = category; }
    public void setFields(ArrayList<Field> fields) { this.fields = fields; }

    public Item addField(Field field) {
        fields.add(field);
        return this;
    }

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
        if (json != null && json.containsKey(API.ICON_KEY) && json.containsKey(API.TITLE_KEY) && json.containsKey(API.CATEGORY_KEY) && json.containsKey(API.FIELDS_KEY)){
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

    public void reset() {
        setupItem();
    }
}
