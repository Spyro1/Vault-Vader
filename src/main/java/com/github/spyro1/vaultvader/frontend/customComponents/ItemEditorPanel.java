package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.backend.Field;
import com.github.spyro1.vaultvader.backend.FieldType;
import com.github.spyro1.vaultvader.backend.Item;
import com.github.spyro1.vaultvader.frontend.UI;
import com.github.spyro1.vaultvader.frontend.ui.MainUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Vector;

public class ItemEditorPanel extends JPanel implements JSONSerializable {

    // Components
    JPanel titleRow;
        IconButton iconButton;
        DarkTextField titleField;
        IconButton moreOptionsButton;
    FieldPanel categoryBox;
    IconButton addNewFieldButton;
    JPanel bottomToolPanel;

    // Referenced variables
    MainUI window;
    Item displayedItem;


    public LinkedList<FieldPanel> fieldsList = new LinkedList<>();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    int gridY = 0;
    String iconFilePath = "picture.png";

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
            iconButton = new IconButton("", iconFilePath); {
                iconButton.setToolTipText("Ikon hozzáadása");
                iconButton.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
                iconButton.addActionListener(this::iconSelectorButtonClicked);
            }
            titleRow.add(iconButton, BorderLayout.WEST);
            titleField = new DarkTextField("", "Bejegyzés címe", true);
            titleRow.add(titleField, BorderLayout.CENTER);
            moreOptionsButton = new IconButton("", "ui-dots.png"); {
                moreOptionsButton.setToolTipText("Egyéb lehetőségek");
                moreOptionsButton.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
                moreOptionsButton.addActionListener(this::moreOptionsButtonClicked);
            }
            titleRow.add(moreOptionsButton, BorderLayout.EAST);
        }
        bottomToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
            bottomToolPanel.setOpaque(false);
            addNewFieldButton = new IconButton("Mező hozzáadása", "plus.png"); {
                addNewFieldButton.setToolTipText("Mező hozzáadása");
                addNewFieldButton.addActionListener(this::addNewFieldButtonClicked);
            }
            bottomToolPanel.add(addNewFieldButton);
        }
    }



    public void hidePanel() {
        this.setVisible(false);
    }

    public void displayItem(Item displayedItem) {
        this.displayedItem = displayedItem;
        removeAll(); // clears panel
        gbc.weighty = 0.01;
        // Add Title
        gbc.gridy = gridY++;
        add(titleRow, gbc);
        // Add Category
        gbc.gridy = gridY++;
        categoryBox = new FieldPanel(new DarkComboField(API.getCategoryList(), displayedItem.getCategoryIdx(), displayedItem.getCategory().getFieldName(), true), displayedItem.getCategory());
        add(categoryBox, gbc);
        // Add fields
        try {
            if (displayedItem != null) {
                iconButton.setIcon(new ImageIcon("", displayedItem.getIcon()));
                titleField.setText(displayedItem.getTitle());
                for (int i = 0; i < displayedItem.getFields().size(); i++) {
                    String fieldName = displayedItem.getFields().get(i).getFieldName();
                    String text = displayedItem.getFields().get(i).getValue();
                    FieldPanel fp = new FieldPanel(fieldName, text, displayedItem.getFields().get(i).getType());
//                    FieldPanel fp = switch (displayedItem.getFields().get(i).getType()) {
//                        case TEXT -> new FieldPanel(new DarkTextField(text, displayedItem.getFields().get(i).getFieldName(), true));
//                        case PASS -> new FieldPanel(new DarkPassField(text, fieldName, true));
//                        case CATEGORY -> new FieldPanel(new DarkComboField(API.getCategoryList(), displayedItem.getCategoryIdx(), fieldName, true));
//                        case null, default -> null;
//                    };
                    if (fp != null) {
                        fieldsList.add(fp);
                        gbc.gridy = gridY++;
                        add(fp, gbc);
                    }
                }
            }
        } catch (Exception e){
            System.err.println("ERROR/ItemEditorPanel/ " + e);
        }

        gbc.gridy = gridY++;
        add(bottomToolPanel, gbc);
        JPanel fillerPanel = new JPanel(); {
            fillerPanel.setOpaque(false);
        }
        gbc.weighty = 0.9;
        gbc.gridy = gridY++;
        add(fillerPanel, gbc);
        validate();
        setVisible(true);
    }
    private void addRow(JComponent component){

    }

    // == Getters ==

    public String getTitle(){
        return titleField.getText();
    }
    public ImageIcon getIcon(){
        return (ImageIcon) iconButton.getIcon();
    }
    public int getCategoryIdx() {
        return displayedItem.getCategoryIdx();
//        return ((DarkComboField)categoryBox.dataField).getSelectedIndex();
//        return ((DarkComboField)categoryBox.dataField).getSelectedItem().toString(); // TODO: ALWAYS ERROR HERE
    }
    // == Click events ==

    private void iconSelectorButtonClicked(ActionEvent actionEvent) {
        FileDialog fd = new FileDialog(window);
        fd.setDirectory("C:\\");
        fd.setFile("*.png|*.jpg|*.jpeg|*.gif");
        fd.setVisible(true);
        iconFilePath = fd.getFile();
        if (iconFilePath == null)
            System.out.println("DEBUG/ItemEditorPanel/iconButton: You cancelled the choice"); // Dor
        else{
            System.out.println("DEBUG/ItemEditorPanel/iconButton: You chose " + iconFilePath);
            displayedItem.setIcon(iconFilePath);
            displayItem(displayedItem); // refresh
        }
    }

    private void addNewFieldButtonClicked(ActionEvent actionEvent) {
        DarkPopupMenuItem newStringField = new DarkPopupMenuItem("Új szöveg mező", _ -> addNewFieldPopupClicked(FieldType.TEXT));
        DarkPopupMenuItem newPassField = new DarkPopupMenuItem("Új jelszó mező", _ -> addNewFieldPopupClicked(FieldType.PASS));
        DarkPopupMenu popup = new DarkPopupMenu(newStringField, newPassField);
        popup.show(actionEvent);
    }

    private void addNewFieldPopupClicked(FieldType chosenType) {
        String fieldName = JOptionPane.showInputDialog(this,"Írja be az új mező nevét!", "Új mező", JOptionPane.QUESTION_MESSAGE);
        if (fieldName != null && !fieldName.isBlank()) {
            displayItem(API.getTemporalItem().addField(new Field(fieldName, "", chosenType)));
        }
    }

    private void moreOptionsButtonClicked(ActionEvent actionEvent) {
        DarkPopupMenuItem deleteItem = new DarkPopupMenuItem("Bejegyzés törlése", this::deleteThisItem);
        DarkPopupMenu more = new DarkPopupMenu(deleteItem);
        more.show(actionEvent);
    }

    private void deleteThisItem(ActionEvent actionEvent) {
    }

    /**
     * @JSONkeys "icon", "title", "category", "fields"
     */
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(API.ICON_KEY, ((ImageIcon)iconButton.getIcon()).getDescription());
        json.put(API.TITLE_KEY, titleField.getText());
        json.put(API.CATEGORY_KEY, ((DarkComboField)categoryBox.component).getSelectedIndex());
        JSONArray fieldsJSON = new JSONArray();
        for (FieldPanel fp : fieldsList) {
            fieldsJSON.add(fp.toJSON());
        }
        json.put(API.FIELDS_KEY, fieldsList);
        return json;
    }

    /**
     * @JSONkeys "id", "icon", "title", "category", "fields"
     */
    @Override
    public Object fromJSON(JSONObject json) {
        return null; // TODO: Write Item editor panel fromJSON this later
    }
}
