package backend.item;

import backend.JSONSerializable;
import backend.fields.*;
import backend.category.*;

import java.util.ArrayList;
import org.json.simple.*;

import javax.swing.*;

public class Item implements JSONSerializable {
    public int ID;
    private ImageIcon icon;
    private String title;
    private Category category;
    private ArrayList<Field> fields = new ArrayList<>();

    public Item() {
        setupItem();
    }
    public Item(Category category) {
        setupItem();
        this.category = category;
    }

    private void setupItem(){
        title = "";
        category = null;
        fields.clear();
    }
    public void initDefaultFields() {
        fields.add(new TextField("Név", ""));
        fields.add(new PassField("Jelszó", ""));
    }

    public String toString() {
        StringBuilder str = new StringBuilder(String.format("{Title: %s, Category: %s, Fields: [", title, category.toString()));
        for (int i = 0; i < fields.size(); i++) {
            str.append(fields.get(i).toString());
            if (i < fields.size() - 1) {
                str.append(", ");
            }
        }
        str.append("]}");
        return str.toString();
    }
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("category", category.toJSON());
        JSONArray fieldsArray = new JSONArray();
        for (Field field : fields) {
            fieldsArray.add(field.toJSON());
        }
        obj.put("fields", fieldsArray);
        return obj;
    }

    @Override
    public Item fromJSON(JSONObject json) {
        title = json.get("title").toString();
        category = new Category(json);
        fields.clear();
        JSONArray fieldsArray = (JSONArray) json.get("fields");
        for (int i = 0; i < fieldsArray.size(); i++) {
            JSONObject jsonFieldData = (JSONObject) fieldsArray.get(i);
            String fieldType = jsonFieldData.get("type").toString();
            switch (fieldType){
                case "IntField":
                    fields.add(new IntField().fromJSON(json));
                    break;
                case "TextField":
                    fields.add(new TextField().fromJSON(json));
                    break;
                case "PassField":
                    fields.add(new PassField().fromJSON(json));
                    break;
                default: break;
            }
        }
        return this;
    }
}