package com.github.spyro1.vaultvader.frontend.customcomponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class DarkPopupMenuItem extends JMenuItem {
    
    public DarkPopupMenuItem(String text, ActionListener actionListener) {
        super(text);
        setForeground(UI.mainTextColor);
        setOpaque(false);
        addActionListener(actionListener);
    }
}
