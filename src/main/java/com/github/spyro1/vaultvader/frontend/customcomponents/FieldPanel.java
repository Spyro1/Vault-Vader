package com.github.spyro1.vaultvader.frontend.customcomponents;

import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.backend.Field;
import com.github.spyro1.vaultvader.backend.FieldType;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

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
    
    private void createField() {
        this.component = switch (data.getType()) {
            case TEXT -> new DarkTextField(data.getValue(), data.getFieldName(), true);
            case PASS -> new DarkPassField(data.getValue(), data.getFieldName(), true);
//            case CATEGORY -> new DarkComboField(API.getCategoryList(), displayedItem.getCategoryIdx(), fieldName, true);
            default -> null; // throw new Exception("ERROR/FieldPanel: Incorrect type");
        };
    }
    
    private void setup() {
        setLayout(new BorderLayout(UI.margin, UI.margin));
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
        
        optionsButton = new IconButton("", "ui-dots.png");
        optionsButton.setToolTipText("Mező opciók");
        optionsButton.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
        
        JPanel panel = new JPanel(new BorderLayout(UI.margin, UI.margin));
        {
            panel.setOpaque(false);
            panel.add(component, BorderLayout.CENTER);
            panel.add(optionsButton, BorderLayout.EAST);
        }
        add(panel);
    }
    
    @Override
    public JSONObject toJSON() {
        return ((JSONSerializable) component).toJSON();
    }
    
    @Override
    public FieldPanel fromJSON(JSONObject json) {
        data.fromJSON(json);
        createField();
        return this;
    }
}
