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
    private String category;
    private ArrayList<Field> fields = new ArrayList<>();

    public Item() {
        setupItem();
    }
    public Item(String category) {
        setupItem();
        this.category = category;
    }

    private void setupItem(){
        title = "";
        category = null;
        fields.clear();
        initDefaultFields();
    }
    public void initDefaultFields() {
        fields.add(new TextField("Név", ""));
        fields.add(new PassField("Jelszó", ""));
    }

    public String toString() {
        return String.format("%s\n[%s]", title, category);
    }
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("title", title);
        obj.put("category", category);
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
        category = json.get("category").toString();
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
