package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class DarkComboField extends JComboBox<String> implements JSONSerializable {

    private String fieldName;

    public DarkComboField(String[] values, String fieldName) {
        this(values, fieldName, false);
    }
    public DarkComboField(String[] values, String fieldName, boolean underline) {
        super(values);
        this.fieldName = fieldName;
        setup();
        setUnderline(underline);
    }
    private void setup(){
        setForeground(UI.mainTextColor);
        setBackground(UI.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
//        setCaretColor(UI.mainTextColor);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor));
    }
    public void setUnderline(boolean underline) {
        if (underline)
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0, UI.mainTextColor), BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor)));
        else
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor));
    }

    /**
     * @JSONkeys "fieldName", "type", "vale"
     */
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(API.TYPE_KEY, API.COMBO_TYPE);
        json.put(API.VALUE_KEY, getSelectedItem().toString());
        json.put(API.FIELD_NAME_KEY, fieldName);
        return json;
    }

    /**
     * @JSONkeys "fieldName", "type", "vale"
     */
    @Override
    public Object fromJSON(JSONObject json) {
        if (json.containsKey(API.VALUE_KEY)) {
            setSelectedItem(json.get(API.VALUE_KEY).toString());
        }
        return this;
    }
}
