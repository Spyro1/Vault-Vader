package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {

    public static final String defaultIconPath = "picture.png";
    private String iconPath;

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
        setIcon(resourcePath);
        setup();
    }
    public void setIcon(String path) {
        ImageIcon icon;
        iconPath = path;
        try {
            icon = new ImageIcon(this.getClass().getClassLoader().getResource(path));
        } catch (Exception ex) {
            try {
                icon = new ImageIcon(path);
            } catch (Exception ex2) {
                System.err.println("ERROR/IconButton: resources/" + path + " file is not found at location");
                icon = new ImageIcon(this.getClass().getClassLoader().getResource(defaultIconPath));
            }
        }
        setIcon(icon);
    }
    public String getIconPath() {
        return iconPath;
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
