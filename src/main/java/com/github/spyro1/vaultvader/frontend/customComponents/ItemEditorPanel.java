package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.backend.API;
import com.github.spyro1.vaultvader.backend.fields.FieldType;
import com.github.spyro1.vaultvader.frontend.UI;
import com.github.spyro1.vaultvader.frontend.ui.MainUI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class ItemEditorPanel extends JPanel {

    JPanel titleRow;
    IconButton iconButton;
    DarkTextField titleField;
    IconButton addNewFieldButton;

    MainUI window;

    public LinkedList<FieldPanel> fieldsList = new LinkedList<>();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    int gridY = 0;

    public ItemEditorPanel(MainUI window) {
        setLayout(gbl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weighty = 0.01;
        gbc.weightx = 1;
        setBackground(UI.bgLightColor);
        titleRow = new JPanel(new BorderLayout()); {
            titleRow.setOpaque(false);
            titleRow.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
            iconButton = new IconButton("", new ImageIcon("assets/white/picture.png")); {
                iconButton.setToolTipText("Ikon hozzáadása");
                iconButton.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
                iconButton.addActionListener(this::iconSelectorButtonClicked);
            }
            titleRow.add(iconButton, BorderLayout.WEST);
            titleField = new DarkTextField("", "Bejegyzés címe", true);
            titleRow.add(titleField, BorderLayout.CENTER);
            // TODO: json ne legyen null teszteket írni
        }
        addNewFieldButton = new IconButton("Mező hozzáadása", new ImageIcon("assets/white/plus.png")); {
            addNewFieldButton.addActionListener(this::addNewFieldButtonClciked);
        }
    }

    private void iconSelectorButtonClicked(ActionEvent actionEvent) {
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
    }

    private void addNewFieldButtonClciked(ActionEvent actionEvent) {
        JPopupMenu pm = new JPopupMenu(); {
            pm.setBackground(UI.bgDarkColor);
            pm.setForeground(UI.mainTextColor);
            pm.setBorder(BorderFactory.createLineBorder(UI.secondaryTextColor));
            JMenuItem mi1 = new JMenuItem("Új szöveg mező"); {
                mi1.setForeground(UI.mainTextColor);
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
                mi2.setForeground(UI.mainTextColor);
                mi2.setOpaque(false);
                mi2.addActionListener(_ -> {System.out.println("Jelszó clicked"); });
            }
            pm.add(mi1);
            pm.add(mi2);
        }
        Component source = (Component)actionEvent.getSource();
        Dimension size = source.getSize();
        int xPos = ((size.width - pm.getPreferredSize().width) / 2);
        int yPos = size.height;
        pm.show(source, xPos, yPos);

    }

    public void hideItem() {
        this.setVisible(false);
    }

    public void displayItem(JSONObject displayedItem) {
        removeAll(); // clears panel
        gbc.weighty = 0.01;
        gbc.gridy = gridY++;
        add(titleRow, gbc);
        try {
            if (displayedItem != null){
                if (displayedItem.containsKey(API.ICON_KEY)) iconButton.setIcon(new ImageIcon(displayedItem.get(API.ICON_KEY).toString()));
                if (displayedItem.containsKey(API.TITLE_KEY)) titleField.setText(displayedItem.get(API.TITLE_KEY).toString());
                if (displayedItem.containsKey(API.FIELDS_KEY)) {
                    JSONArray fieldsJSON = (JSONArray) displayedItem.get(API.FIELDS_KEY);
                    if (fieldsJSON != null){
                        for (int i = 0; i < fieldsJSON.size(); i++) {
                            JSONObject field = (JSONObject) fieldsJSON.get(i);
                            String fieldName = field.get(API.FIELD_NAME_KEY).toString();
                            String value = field.get(API.VALUE_KEY).toString();
                            FieldType type = FieldType.fromString(field.get(API.TYPE_KEY).toString());
                            FieldPanel fp = null; //= new FieldPanel(new DarkTextField(value, fieldName, true));
                            switch (type) {
                                case TEXT:
                                    fp = new FieldPanel(new DarkTextField(value, fieldName, true));
                                    break;
                                case PASS:
                                    fp = new FieldPanel(new DarkPassField(value, fieldName, true));
                                    break;
                                case CATEGORY:
                                    fp = new FieldPanel(new DarkComboField((new String[] {"elso", "masodik", "harmadik"}), fieldName, true));
                                    break;
                                case null:
                                default:
                                    fp = new FieldPanel(new DarkTextField(value, fieldName, true));
                                    break;
                            }
                            fieldsList.add(fp);
                            gbc.gridy = gridY++;
                            add(fp, gbc);
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println("ERROR/ItemEditorPanel/ " + e);
        }

        gbc.gridy = gridY++;
        add(addNewFieldButton, gbc);
        JPanel fillerPanel = new JPanel(); {
            fillerPanel.setOpaque(false);
        }
        gbc.weighty = 0.9;
        gbc.gridy = gridY++;
        add(fillerPanel, gbc);
        validate();
        setVisible(true);
    }
}
