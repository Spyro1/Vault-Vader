package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class FieldPanel extends JPanel {

    public IconButton optionsButton;
//    public DarkTextField textField;
    public JComponent dataField;
//    JLabel fieldLabel = new JLabel();

    public FieldPanel(JComponent dataField) {
        this.dataField = dataField;
//        if (this.dataField.getClass() == DarkTextField.class) {
//        }
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));

        optionsButton = new IconButton("", "ui-dots.png");
        optionsButton.addActionListener(this::moreOptionsButtonClicked);

        JPanel panel = new JPanel(new BorderLayout()); {
            panel.setOpaque(false);
            panel.add(dataField, BorderLayout.CENTER);
            panel.add(optionsButton, BorderLayout.EAST);
        }
        add(panel);
    }

    private void moreOptionsButtonClicked(ActionEvent actionEvent) {
        System.out.println("Megkattintottak!");
    }
}
