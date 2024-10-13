package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.Objects;

public class DarkPassField extends JPasswordField implements JSONSerializable {

    private final String fieldName;
    private final char defChar = getEchoChar();

    private JToggleButton passwordShowToggler;

    public DarkPassField(String text, String fieldName) {
        this(text, fieldName, false);
    }
    public DarkPassField(String text, String fieldName, boolean underline) {
        super(API.dencryptData(text, fieldName));
//        super(text);
        this.fieldName = fieldName;
        setup();
        setUnderline(underline);
    }
    private void setup(){
        setLayout(new BorderLayout());
        setForeground(UI.mainTextColor);
        setBackground(UI.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setCaretColor(UI.mainTextColor);
        passwordShowToggler = new JToggleButton(); {
            passwordShowToggler.setBackground(UI.bgLightColor);
            passwordShowToggler.setUI(new MetalToggleButtonUI(){
                @Override
                protected Color getSelectColor() {
                    return UI.secondaryTextColor;
                }
            });
            passwordShowToggler.setToolTipText("Jelszó megjelenítése");
            passwordShowToggler.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("eye-closed.png"))));
            passwordShowToggler.setBorder(null);
            passwordShowToggler.addItemListener(this::togglePasswordButtonClcicked);
        }
        add(passwordShowToggler, BorderLayout.EAST);
    }
    public void setUnderline(boolean underline) {
        TitledBorder tb = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), fieldName, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor);
        MatteBorder mb = BorderFactory.createMatteBorder(0,0,2,0, UI.mainTextColor);
        if (underline)
            setBorder(BorderFactory.createCompoundBorder(tb, mb));
        else
            setBorder(BorderFactory.createCompoundBorder(tb, null));
    }
    public void showPassword(boolean shown){
        if (shown) {
            setEchoChar((char) 0);
        } else {
            setEchoChar(defChar);
        }
    }
    private void togglePasswordButtonClcicked(ItemEvent e) {
        showPassword(e.getStateChange() == ItemEvent.SELECTED);
        if (e.getStateChange() == ItemEvent.SELECTED) {
            passwordShowToggler.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("eye.png"))));
            passwordShowToggler.setToolTipText("Jelszó elrejtése");
        } else {
            passwordShowToggler.setIcon(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("eye-closed.png"))));
            passwordShowToggler.setToolTipText("Jelszó megjelenítése");
        }
    }

    /**
     * @return A {@link JSONObject} representing the current object's state in JSON format, null if the field is empty.
     */
    @Override
    public JSONObject toJSON() {
        if (getText().isBlank()) return null; // Empty field
        JSONObject json = new JSONObject();
        json.put(API.TYPE_KEY, API.PASS_TYPE);
        json.put(API.VALUE_KEY, API.encryptData(getText(), fieldName));
        json.put(API.FIELD_NAME_KEY, fieldName);
        return json;
    }

    @Override
    public DarkPassField fromJSON(JSONObject json) {
        // TODO: Write Dark pass fromJSON
//        if (json.containsKey(API.VALUE_KEY)) {
//            setText(json.get(API.VALUE_KEY).toString());
//        }
        return this;
    }
}
