package backend.categories;

import org.json.simple.JSONObject;

import java.awt.*;

public class Category {
    public String categoryName;
    public Color color;

    public Category() {
        this.categoryName = "";
        this.color = Color.WHITE;
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
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("categoryName", categoryName);
        obj.put("red", color.getRed());
        obj.put("green", color.getGreen());
        obj.put("blue", color.getBlue());
        return obj;
    }
}
