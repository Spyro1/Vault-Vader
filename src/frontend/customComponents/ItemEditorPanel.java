package frontend.customComponents;

import backend.API;
import frontend.VV;
import frontend.ui.MainUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class ItemEditorPanel extends JPanel {

    JPanel titleRow;
    IconButton iconButton;
    DarkTextField titleField;
    IconButton addNewField;

    public LinkedList<JTextField> textFieldsList = new LinkedList<>();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    int gridY = 0;

    public ItemEditorPanel(MainUI window) {
//        setLayout(new GridLayout(10,1,VV.margin, VV.margin/2));

        setLayout(gbl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weighty = 0.01;
        gbc.weightx = 1;
        setBackground(VV.bgLightColor);
        titleRow = new JPanel(new BorderLayout()); {
            titleRow.setOpaque(false);
            titleRow.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
            iconButton = new IconButton("", new ImageIcon("assets/white/picture.png")); {
                iconButton.setToolTipText("Ikon hozzáadása");
                iconButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
                iconButton.addActionListener(e -> {
                    FileDialog fd = new FileDialog(window);
                    fd.setDirectory("C:\\");
                    fd.setFile("*.png|*.jpg|*.jpeg|*.gif");
                    fd.setVisible(true);
                    String filename = fd.getFile();
                    if (filename == null)
                        System.out.println("You cancelled the choice");
                    else{
//                        API.saveItem() // TODO: Write icon saving ....
                        System.out.println("You chose " + filename);
                    }
                });
                titleRow.add(iconButton, BorderLayout.WEST);
                titleField = new DarkTextField("", "Bejegyzés címe");
                titleField.setUnderline(true);
                titleRow.add(titleField, BorderLayout.CENTER);
            }
            // TODO: json ne legyen null teszteket írni
        }
        addNewField = new IconButton("Mező hozzáadása", new ImageIcon("assets/white/plus.png"));
    }

    public void displayItem(JSONObject displayedItem) {
        removeAll(); // clears panel
        add(titleRow, gbc);
        gbc.gridy = gridY++;
        if (displayedItem != null){
            if (displayedItem.containsKey(API.ICON_KEY)) iconButton.setIcon(new ImageIcon(displayedItem.get(API.ICON_KEY).toString()));
            if (displayedItem.containsKey(API.TITLE_KEY)) titleField.setText(displayedItem.get(API.TITLE_KEY).toString());
            if (displayedItem.containsKey(API.FIELDS_KEY)) {
                JSONArray fieldsJSON = (JSONArray) displayedItem.get(API.FIELDS_KEY);
                if (fieldsJSON != null){
                    for (int i = 0; i < textFieldsList.size(); i++) {
                        JSONObject field = (JSONObject) fieldsJSON.get(i);
                        String fieldName = field.get(API.FIELD_NAME_KEY).toString();
                        String value = field.get(API.VALUE_KEY).toString();
                        textFieldsList.add(new DarkTextField(value, fieldName));
                        add(textFieldsList.getLast(), gbc);
                        gbc.gridy = gridY++;
                    }
                }
            }
        }

        gbc.gridy = gridY++;
        add(addNewField, gbc);
    }
}
