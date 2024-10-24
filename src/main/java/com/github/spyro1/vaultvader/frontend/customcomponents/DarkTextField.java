package com.github.spyro1.vaultvader.frontend.customcomponents;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class DarkTextField extends JTextField implements JSONSerializable {
    
    private final String fieldName;
    
    public DarkTextField(String text, String fieldName) {
        this(text, fieldName, false);
    }
    
    public DarkTextField(String text, String fieldName, boolean underline) {
        super(text);
        this.fieldName = fieldName;
        setup();
        setUnderline(underline);
    }
    
    private void setup() {
        setForeground(UI.mainTextColor);
        setBackground(UI.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setCaretColor(UI.mainTextColor);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0, 0, new Font("Arial", Font.BOLD, 12), UI.mainTextColor));
    }
    
    public void setUnderline(boolean underline) {
        if (underline)
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, UI.mainTextColor), BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0, 0, new Font("Arial", Font.BOLD, 12), UI.mainTextColor)));
        else
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0, 0, new Font("Arial", Font.BOLD, 12), UI.mainTextColor));
    }
    
    /**
     * @return A {@link JSONObject} representing the current object's state in JSON format, null if the field is empty.
     * @JSONkeys "fieldName", "type", "value"
     */
    @Override
    public JSONObject toJSON() {
        if (getText().isBlank()) return null; // Empty field
        JSONObject json = new JSONObject();
        json.put(API.TYPE_KEY, API.TEXT_TYPE);
        json.put(API.VALUE_KEY, getText());
        json.put(API.FIELD_NAME_KEY, fieldName);
        return json;
    }
    
    @Override
    public Object fromJSON(JSONObject json) {
        // TODO: Write Dark text fromJSON
        if (json.containsKey(API.VALUE_KEY)) {
            setText(json.get(API.VALUE_KEY).toString());
        }
        return this;
    }
}
