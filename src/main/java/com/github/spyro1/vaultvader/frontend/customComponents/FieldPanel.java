package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.VV;

import javax.swing.*;
import java.awt.*;

public class FieldPanel extends JPanel {

    public IconButton optionsButton = new IconButton("", new ImageIcon("assets/white/ui-dots.png"));
    public DarkTextField textField;
//    JLabel fieldLabel = new JLabel();

    public FieldPanel(String labelTitle) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
        JPanel panel = new JPanel(new BorderLayout()); {
            panel.setOpaque(false);
            textField = new DarkTextField("", labelTitle, true);
            panel.add(textField, BorderLayout.CENTER);
            optionsButton.addActionListener(e -> {System.out.println("Megkattintottak!");});
            panel.add(optionsButton, BorderLayout.EAST);
        }
        add(panel);
    }
}
