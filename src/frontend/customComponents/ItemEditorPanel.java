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
    IconButton addNewFieldButton;

    public LinkedList<JTextField> textFieldsList = new LinkedList<>();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    int gridY = 0;

    public ItemEditorPanel(MainUI window) {
//        setLayout(new GridLayout(10,1,VV.margin, VV.margin/2));

        setLayout(gbl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
//        gbc.anchor = GridBagConstraints;
        gbc.gridx = 0;
        gbc.weighty = 0.01;
//        gbc.gridy = gridY;
        gbc.weightx = 1;
        setBackground(VV.bgLightColor);
        titleRow = new JPanel(new BorderLayout()); {
            titleRow.setOpaque(false);
            titleRow.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
            iconButton = new IconButton("", new ImageIcon("assets/white/picture.png")); {
                iconButton.setToolTipText("Ikon hozzáadása");
                iconButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
                iconButton.addActionListener(_ -> {
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
        addNewFieldButton = new IconButton("Mező hozzáadása", new ImageIcon("assets/white/plus.png")); {
            addNewFieldButton.addActionListener(e -> {
                JPopupMenu pm = new JPopupMenu(); {
                    pm.setBackground(VV.bgDarkColor);
                    pm.setForeground(VV.mainTextColor);
                    pm.setBorder(BorderFactory.createLineBorder(VV.secondaryTextColor));
                    JMenuItem mi1 = new JMenuItem("Új szöveg mező"); {
                        mi1.setForeground(VV.mainTextColor);
                        mi1.setOpaque(false);
                        mi1.addActionListener(_ -> {
                            System.out.println("szöveg clicked");
                            JSONObject json = new JSONObject();
                            json.put(API.TYPE_KEY, API.TEXT_TYPE);
                            API.addNewField(json, window.selectedItemIndex);
                            displayItem(API.getItemData(window.selectedItemIndex)); // Refresh
                        });
                    }
                    JMenuItem mi2 = new JMenuItem("Új jelszó mező"); {
                        mi2.setForeground(VV.mainTextColor);
                        mi2.setOpaque(false);
                        mi2.addActionListener(_ -> {System.out.println("Jelszó clicked"); });
                    }
                    pm.add(mi1);
                    pm.add(mi2);
                }
//                API.addNewField();
                Component source = (Component)e.getSource();
                Dimension size = source.getSize();
                int xPos = ((size.width - pm.getPreferredSize().width) / 2);
                int yPos = size.height;
                pm.show(source, xPos, yPos);

            });
        }
    }

    public void displayItem(JSONObject displayedItem) {
        removeAll(); // clears panel
        gbc.weighty = 0.01;
        gbc.gridy = gridY++;
        add(titleRow, gbc);
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

//        gbc.weighty = 0.1;
        gbc.gridy = gridY++;
        add(addNewFieldButton, gbc);
        JPanel fillerPanel = new JPanel(); {
            fillerPanel.setOpaque(false);
//            fillerPanel.setBackground(VV.bgLightColor);
        }
        gbc.weighty = 0.9;
        gbc.gridy = gridY++;
        add(fillerPanel, gbc);
    }
}
