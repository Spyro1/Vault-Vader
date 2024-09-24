package frontend.customComponents;

import backend.API;
import com.sun.tools.javac.Main;
import frontend.VV;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ItemEditorPanel extends JPanel {

    public LinkedList<JTextField> fields;
    public ItemEditorPanel(JSONObject item) {
        setLayout(new GridLayout(10,1,VV.margin, VV.margin/2));
        setBackground(VV.bgLightColor);
        JPanel titleRow = new JPanel(new BorderLayout()); {
            titleRow.setOpaque(false);
            titleRow.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
            IconButton iconButton = new IconButton("", new ImageIcon("assets/white/picture.png")); {
                iconButton.setToolTipText("Ikon hozzáadása");
                iconButton.addActionListener(e -> {
                    FileDialog fd = new FileDialog((Frame) getParent());
                    fd.setDirectory("C:\\");
                    fd.setFile("*.png|*.jpg|*.jpeg|*.gif");
                    fd.setVisible(true);
                    String filename = fd.getFile();
                    if (filename == null)
                        System.out.println("You cancelled the choice");
                    else
                        System.out.println("You chose " + filename);
                });
            }
            // TODO: json ne legyen null teszteket írni
            iconButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
            titleRow.add(iconButton, BorderLayout.WEST);
            DarkTextField titleField = new DarkTextField(item.get(API.TITLE_KEY).toString(), "Bejegyzés címe");
            titleField.setUnderline(true);
            titleRow.add(titleField, BorderLayout.CENTER);
        }
        add(titleRow);
        JSONArray fieldsJSON = (JSONArray) item.get(API.FIELDS_KEY);
        for (int i = 0; i < fields.size(); i++) {
            JSONObject field = (JSONObject) fieldsJSON.get(i);
            String fieldName = field.get(API.FIELD_NAME_KEY).toString();
            String value = field.get(API.VALUE_KEY).toString();
            fields.add(new DarkTextField(value, fieldName));
            add(fields.getLast());
        }
    }
}
