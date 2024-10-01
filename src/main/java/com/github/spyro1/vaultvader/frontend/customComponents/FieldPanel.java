package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class FieldPanel extends JPanel {

    public IconButton optionsButton = new IconButton("", new ImageIcon(this.getClass().getClassLoader().getResource("ui-dots.png")));
//    public DarkTextField textField;
    public JComponent componentField;
//    JLabel fieldLabel = new JLabel();

    public FieldPanel(JComponent componentField) {
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
//        try {
//            optionsButton = new IconButton("", (Icon) ImageIO.read(getClass().getResource("/resources/ui-dots.png"))); // new ImageIcon(this.getClass().getClassLoader().getResource("ui-dots.png"));
//        } catch (IOException e) {
//            System.err.println("ERROR/FieldPanel: options button could not be loaded");
//        }
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
