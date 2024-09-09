package backend;

import java.util.ArrayList;

public class ItemManager {
    private ArrayList<Item> items = new ArrayList<>();;

    public ItemManager() {

    }

    public void addItem(Item item) {
        items.add(item);
    }
}
