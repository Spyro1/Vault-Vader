import fields.*;

import java.util.ArrayList;

public class Item {
    private ArrayList<Field> fileds = new ArrayList<Field>();

    public Item() {
        fileds.add(new TextField("Cím", ""));
        fileds.add(new TextField("Név", ""));
        fileds.add(new PassField("Jelszó", ""));
    }

}
