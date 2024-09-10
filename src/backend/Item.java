package backend;

import backend.fields.*;
import backend.categories.*;

import java.util.ArrayList;
import org.json.simple.*;

import javax.swing.*;

public class Item {
    public int ID;
    private ImageIcon icon;
    private String title;
    private Category category = new Category();
    private ArrayList<Field> fields = new ArrayList<>();

    public Item() {
        setupItem();
    }
    public Item(Category category) {
        this.category = category;
        setupItem();
    }

    private void setupItem(){
        title = "";
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
}
