package backend.item;

import backend.API;
import backend.JSONSerializable;
import backend.fields.*;

import java.util.ArrayList;
import org.json.simple.*;

import javax.swing.*;

public class Item implements JSONSerializable {
    private static int idCounter = 0;
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
        ID = idCounter++;
        title = "";
        category = null;
        icon = new ImageIcon("assets/white/picture.png", "assets/white/picture.png");
        fields.clear();
        fields.add(new TextField("Név", ""));
        fields.add(new PassField("Jelszó", ""));
    }

    public ImageIcon getIcon() { return icon; }
    public String getTitle() { return title; }
    public String getCategory() { return category; }
    public ArrayList<Field> getFields() { return fields; }

    public void setIcon(ImageIcon icon) { this.icon = icon; }
    public void setTitle(String title) { this.title = title; }
    public void setCategory(String category) { this.category = category; }
    public void setFields(ArrayList<Field> fields) { this.fields = fields; }

    @Override
    public String toString() {
        return String.format("%s [%s]", title, category);
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put(API.ID_KEY, ID);
        obj.put(API.ICON_KEY, icon.getDescription());
        obj.put(API.TITLE_KEY, title);
        obj.put(API.CATEGORY_KEY, category);
        JSONArray fieldsArray = new JSONArray();
        for (Field field : fields) {
            fieldsArray.add(field.toJSON());
        }
        obj.put(API.FIELDS_KEY, fieldsArray);
        return obj;
    }

    @Override
    public Item fromJSON(JSONObject json) {
        if (json.get(API.ID_KEY) != null) ID = Integer.parseInt(json.get(API.ID_KEY).toString());
        if (json.get(API.ICON_KEY) != null) icon = new ImageIcon(json.get(API.ICON_KEY).toString());
        if (json.get(API.TITLE_KEY) != null) title = json.get(API.TITLE_KEY).toString();
        if (json.get(API.CATEGORY_KEY) != null) category = json.get(API.CATEGORY_KEY).toString();
        fields.clear();
        if (json.get(API.FIELDS_KEY) != null) {
            JSONArray fieldsArray = (JSONArray) json.get(API.FIELDS_KEY);
            for (int i = 0; i < fieldsArray.size(); i++) {
                JSONObject jsonFieldData = (JSONObject) fieldsArray.get(i);
                String fieldType = jsonFieldData.get(API.TYPE_KEY).toString();
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
        }
        return this;
    }
}
