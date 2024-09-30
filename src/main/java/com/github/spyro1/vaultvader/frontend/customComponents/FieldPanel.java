package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel {

    public IconButton optionsButton = new IconButton("", new ImageIcon("assets/white/ui-dots.png"));
//    public DarkTextField textField;
    public JComponent componentField;
//    JLabel fieldLabel = new JLabel();

    public FieldPanel(JComponent componentField) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
        JPanel panel = new JPanel(new BorderLayout()); {
            panel.setOpaque(false);
//            textField = new DarkTextField("", labelTitle, true);
            this.componentField = componentField;
            panel.add(componentField, BorderLayout.CENTER);
            optionsButton.addActionListener(e -> {System.out.println("Megkattintottak!");});
            panel.add(optionsButton, BorderLayout.EAST);
        }
        add(panel);
    }
}
