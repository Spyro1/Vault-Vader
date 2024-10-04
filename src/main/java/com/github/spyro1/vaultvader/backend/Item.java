package com.github.spyro1.vaultvader.backend;

import java.util.ArrayList;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.*;

public class Item implements JSONSerializable {
    private static int idCounter = 0;

    public int ID;
    private String icon;
    private String title;
    private Field category;
    private int categoryIdx;
    private ArrayList<Field> fields = new ArrayList<>();

    public Item() {
        setupItem();
    }

    public Item(int categoryIdx) {
        setupItem();
        this.categoryIdx = categoryIdx;
    }

    private void setupItem(){
        ID = idCounter++;
        title = null;
        categoryIdx = 0;
        category = new Field("Kategoria", "", FieldType.CATEGORY);
        final String defaultIconPath = "picture.png";
        icon = defaultIconPath; //new ImageIcon(this.getClass().getClassLoader().getResource(defaultIconPath), defaultIconPath);
        fields.clear();
//        fields.add(new Field("Kategória", "", FieldType.CATEGORY));
        fields.add(new Field("Felhasználónév", "", FieldType.TEXT));
        fields.add(new Field("Jelszó", "", FieldType.PASS));
    }

    public String getIcon() { return icon; }
    public String getTitle() { return title; }
    public Field getCategory() { return category; }
    public int getCategoryIdx() { return categoryIdx; }
    public ArrayList<Field> getFields() { return fields; }

    public void setIcon(String icon) { this.icon = icon; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(Field category) { this.category = category; }
    public void setCategoryIdx(int categoryIdx) { this.categoryIdx = categoryIdx; }
    public void setFields(ArrayList<Field> fields) { this.fields = fields; }

    public Item addField(Field field) {
        fields.add(field);
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s [%s]", title, categoryIdx);
    }

    /**
     * @JSONkeys "id", "icon", "title", "category", "fields"
     */
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.ID_KEY, ID);
        obj.put(API.ICON_KEY, icon);
        obj.put(API.TITLE_KEY, title);
        obj.put(API.CATEGORY_KEY, categoryIdx);
        JSONArray fieldsArray = new JSONArray();
        for (Field field : fields) {
            fieldsArray.add(field.toJSON());
        }
        obj.put(API.FIELDS_KEY, fieldsArray);
        return obj;
    }

    /**
     * @JSONkeys "id", "icon", "title", "category", "fields"
     */
    @Override
    public Item fromJSON(JSONObject json) {
        if (json != null){
            if (json.containsKey(API.ID_KEY)) ID = Integer.parseInt(json.get(API.ID_KEY).toString());
            if (json.containsKey(API.ICON_KEY)) icon = json.get(API.ICON_KEY).toString(); //new ImageIcon(this.getClass().getClassLoader().getResource(json.get(API.ICON_KEY).toString()), json.get(API.ICON_KEY).toString());
            if (json.containsKey(API.TITLE_KEY)) title = json.get(API.TITLE_KEY).toString();
            if (json.containsKey(API.CATEGORY_KEY)) categoryIdx = (int) json.get(API.CATEGORY_KEY);
            fields.clear();
            if (json.containsKey(API.FIELDS_KEY)) {
                JSONArray fieldsArray = (JSONArray) json.get(API.FIELDS_KEY);
                for (int i = 0; i < fieldsArray.size(); i++) {
                    JSONObject jsonFieldData = (JSONObject) fieldsArray.get(i);
                    fields.add(new Field().fromJSON(jsonFieldData));
                }
            }
            return this;
        }
        else {
            return null;
        }
    }

    public void reset() {
        setupItem();
    }
}
