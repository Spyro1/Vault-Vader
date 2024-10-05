package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Vector;

public class DarkComboField extends JComboBox<String> implements JSONSerializable {

    private String fieldName;

    public DarkComboField(Collection<String> values, int selectedIdx, String fieldName) {
        this(values, selectedIdx, fieldName, false);
    }
    public DarkComboField(Collection<String> values, int selectedIdx, String fieldName, boolean underline) {
        super(new Vector<String>(values));
        this.fieldName = fieldName;
        setup();
        setUnderline(underline);
        setSelectedIndex(selectedIdx);
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
     * @JSONkeys "fieldName", "type", "value"
     */
    @Override
    public JSONObject toJSON() {
        if (getSelectedIndex() == -1) return null; // Not selected any item
        JSONObject json = new JSONObject();
        json.put(API.TYPE_KEY, API.COMBO_TYPE);
        json.put(API.VALUE_KEY, getSelectedItem().toString());
        json.put(API.FIELD_NAME_KEY, fieldName);
        return json;
    }

    /**
     * @JSONkeys "fieldName", "value", "values"
     */
    @Override
    public Object fromJSON(JSONObject json) {
        // String fieldName = "";
        // String[] values; // = new String[0];
        // if (json.containsKey(API.VALUE_KEY)) setSelectedItem(json.get(API.VALUE_KEY).toString());
        // TODO: Write Dark combo to set values
        // if(json.containsKey(API.VALUES_KEY)) values = (String[]) json.get(API.VALUES_KEY);
        // if (json.containsKey(API.FIELD_NAME_KEY)) fieldName = json.get(API.FIELD_NAME_KEY).toString();
        return this;
    }
}
