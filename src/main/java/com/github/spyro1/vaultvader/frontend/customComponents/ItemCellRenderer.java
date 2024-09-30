package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.backend.Item;
import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import java.awt.*;

public class ItemCellRenderer extends JPanel implements ListCellRenderer<Item> {

    private final JLabel  titleLabel = new JLabel();
    private final JLabel categoryLabel = new JLabel();
    private final JButton icon = new JButton();

    public ItemCellRenderer() {
        setLayout(new BorderLayout(5, 5));
        setBackground(UI.bgLightColor);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        /* inner panel: */
        JPanel innerPanel = new JPanel();
        {
            innerPanel.setLayout(new BorderLayout());
            innerPanel.setOpaque(false);
            JPanel textPanel = new JPanel(new BorderLayout()); {
                textPanel.setBorder(BorderFactory.createEmptyBorder(UI.margin/2, UI.margin/2, UI.margin/2, UI.margin/2));
                textPanel.setOpaque(false);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
//                titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                categoryLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                titleLabel.setForeground(UI.mainTextColor);
                categoryLabel.setForeground(UI.mainTextColor);
                textPanel.add(titleLabel, BorderLayout.CENTER);
                textPanel.add(categoryLabel, BorderLayout.SOUTH);
            }
            icon.setBackground(UI.bgDarkColor);
            icon.setBorder(BorderFactory.createLineBorder(UI.bgDarkColor, UI.margin));
            icon.addActionListener(_ -> System.out.println("Megnyomtak"));
            innerPanel.add(icon, BorderLayout.WEST);
            innerPanel.add(textPanel, BorderLayout.CENTER);
        }
        add(innerPanel, BorderLayout.CENTER);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Item> list, Item value, int index, boolean isSelected, boolean cellHasFocus) {
        titleLabel.setText(value.getTitle());
        categoryLabel.setText(value.getCategory());
        icon.setIcon(value.getIcon());
        // when select item
        if (isSelected) {
            setBackground(UI.mainColor);
//            titleLabel.setForeground(VV.mainTextColor);
//            categoryLabel.setForeground(VV.mainTextColor);
        } else { // when don't select
            setBackground(UI.bgLightColor);
//            titleLabel.setForeground(VV.mainTextColor);
//            categoryLabel.setForeground(VV.mainTextColor);
        }

        return this;
    }
}
