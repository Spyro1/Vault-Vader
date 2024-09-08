package backend.categories;

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
        return "{Category:\"" + categoryName + "\";\"" + color.toString() + "\"}";
    }
}
