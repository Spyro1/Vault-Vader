package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.*;

public class DarkComboField extends JComboBox<String> {
    private String placeholder = "";

    public DarkComboField(String[] values) {
        super(values);
        setup();
    }
    public DarkComboField(String[] values, String placeholder) {
        super(values);
        this.placeholder = placeholder;
        setup();
    }
    public DarkComboField(String[] values, String placeholder, boolean underline) {
        super(values);
        this.placeholder = placeholder;
        setup();
        setUnderline(underline);
    }
    private void setup(){
        setForeground(UI.mainTextColor);
        setBackground(UI.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
//        setCaretColor(UI.mainTextColor);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor));
    }
    public void setUnderline(boolean underline) {
        if (underline)
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0, UI.mainTextColor), BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor)));
        else
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  UI.mainTextColor));
    }
}
