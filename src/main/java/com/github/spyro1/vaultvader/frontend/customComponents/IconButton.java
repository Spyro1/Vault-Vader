package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {

    public static final String defaultIconPath = "picture.png";

    public IconButton() {
        super();
        setup();
    }
    public IconButton(Icon icon) {
        super(icon);
        setup();
    }
    public IconButton(String text) {
        super(text);
        setup();
    }
    public IconButton(String text, Icon icon) {
        super(text, icon);
        setup();
    }
    public IconButton(String text, String resourcePath) {
        super(text);
        ImageIcon icon;
        try {
            icon = new ImageIcon(this.getClass().getClassLoader().getResource(resourcePath));
        } catch (Exception ex) {
            System.err.println("ERROR/IconButton: resources/" + resourcePath + " file is not found at location");
            icon = new ImageIcon(this.getClass().getClassLoader().getResource(defaultIconPath));
        }
        setIcon(icon);
        setup();
    }
    private void setup(){
//        setOpaque(false);
        setBackground(UI.bgLightColor);
        setForeground(UI.mainTextColor);
        setBorder(BorderFactory.createEmptyBorder(UI.margin/2, UI.margin/2, UI.margin/2, UI.margin/2));
        setFont(new Font("Arial", Font.PLAIN, 20));
        setContentAreaFilled(false); // Sets to be able to paint enabled and hover background
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().brighter());
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
