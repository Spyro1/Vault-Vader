package backend;

import backend.fields.*;
import backend.categories.*;
import java.util.ArrayList;

public class Item {
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
        fields.add(new TextField("Cím", ""));
        fields.add(new TextField("Név", ""));
        fields.add(new PassField("Jelszó", ""));
    }

    public String toString() {
        StringBuilder strb = new StringBuilder();
        strb.append("{").append(category.toString()).append(",");
        strb.append("\"Fields\":[");
        for (int i = 0; i < fields.size(); i++) {
            strb.append(fields.get(i).toString());
            if (i < fields.size() - 1) {
                strb.append(",");
            }
        }
        strb.append("]}");
        return strb.toString();
    }
}
