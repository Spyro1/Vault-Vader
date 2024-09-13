package backend.category;

import backend.JSONSerializable;
import org.json.simple.JSONObject;

import java.awt.*;

public class Category implements JSONSerializable {
    public String categoryName;
    public Color color;

    public Category() {
        this.categoryName = "";
        this.color = Color.WHITE;
    }
    public Category(JSONObject json) {
        fromJSON(json);
    }
    public Category(String categoryName) {
        this.categoryName = categoryName;
        this.color = Color.WHITE;
    }
    public Category(String categoryName, Color color) {
        this.categoryName = categoryName;
        this.color = color;
    }

    public String toString() {
        return "{CategoryName: " + categoryName + ", red: " + color.getRed() + ", green: " + color.getGreen() + ", blue: " + color.getBlue() + "}";
    }

    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("categoryName", categoryName);
        obj.put("red", color.getRed());
        obj.put("green", color.getGreen());
        obj.put("blue", color.getBlue());
        return obj;
    }

    @Override
    public Object fromJSON(JSONObject json) {
        categoryName = json.get("categoryName").toString();
        color = new Color((int)json.get("red"), (int)json.get("green"), (int)json.get("blue"));
        return this;
    }
}
