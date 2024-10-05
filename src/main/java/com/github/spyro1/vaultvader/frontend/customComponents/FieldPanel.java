package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.backend.Field;
import com.github.spyro1.vaultvader.backend.FieldType;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FieldPanel extends JPanel implements JSONSerializable {

    public IconButton optionsButton;
    public JComponent component;
    public Field data;

    public FieldPanel(JComponent component, Field data) {
        this.component = component;
        this.data = data;
        setup();
    }
    public FieldPanel(Field data) {
        this.data = data;
        createField();
        setup();
    }
    public FieldPanel(String fieldName, String text, FieldType type) {
        this.data = new Field(fieldName, text, type);
        createField();
        setup();
    }

    private void createField(){
        this.component = switch (data.getType()) {
            case TEXT -> new DarkTextField(data.getValue(), data.getFieldName(), true);
            case PASS -> new DarkPassField(data.getValue(), data.getFieldName(), true);
//            case CATEGORY -> new DarkComboField(API.getCategoryList(), displayedItem.getCategoryIdx(), fieldName, true);
            case null, default -> null; // throw new Exception("ERROR/FieldPanel: Incorrect type");
        };
    }
    private void setup() {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));

        optionsButton = new IconButton("", "ui-dots.png");
        optionsButton.addActionListener(this::moreOptionsButtonClicked);

        JPanel panel = new JPanel(new BorderLayout()); {
            panel.setOpaque(false);
            panel.add(component, BorderLayout.CENTER);
            panel.add(optionsButton, BorderLayout.EAST);
        }
        add(panel);
    }

    private void moreOptionsButtonClicked(ActionEvent actionEvent) {
        DarkPopupMenuItem deleteFieldMenuItem = new DarkPopupMenuItem("Mező törlése", this::deleteThisFieldMenuItemClicked);
        DarkPopupMenuItem renameFieldMenuItem = new DarkPopupMenuItem("Mező átnevezése", this::renameThisFieldMenuItemClicked);
        DarkPopupMenu moreOptions = new DarkPopupMenu(deleteFieldMenuItem, renameFieldMenuItem);
        moreOptions.show(actionEvent);
    }

    private void renameThisFieldMenuItemClicked(ActionEvent e) {
        // TODO: Write rename field
    }

    private void deleteThisFieldMenuItemClicked(ActionEvent e) {
        // TODO: Write delete field
    }

    @Override
    public JSONObject toJSON() {
        return ((JSONSerializable)component).toJSON();
    }

    @Override
    public FieldPanel fromJSON(JSONObject json) {
        data.fromJSON(json);
        createField();
        return this;
    }
}
