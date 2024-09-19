package frontend.customComponents;

import backend.item.Item;
import frontend.VV;

import javax.swing.*;
import java.awt.*;

public class ItemRenderer extends JPanel implements ListCellRenderer<Item> {

    private final JLabel  titleLabel = new JLabel();
    private final JLabel categoryLabel = new JLabel();
    private final JButton icon = new JButton();

    public ItemRenderer() {
        setLayout(new BorderLayout(5, 5));
        setBackground(VV.bgLightColor);
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        /* inner panel: */
        JPanel innerPanel = new JPanel();
        {
            innerPanel.setLayout(new BorderLayout());
            innerPanel.setOpaque(false);
            JPanel textPanel = new JPanel(new BorderLayout()); {
                textPanel.setBorder(BorderFactory.createEmptyBorder(VV.margin/2, VV.margin/2, VV.margin/2, VV.margin/2));
                textPanel.setOpaque(false);
                titleLabel.setFont(new Font("Arial", Font.BOLD, 15));
//                titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                categoryLabel.setFont(new Font("Arial", Font.PLAIN, 10));
                titleLabel.setForeground(VV.mainTextColor);
                categoryLabel.setForeground(VV.mainTextColor);
                textPanel.add(titleLabel, BorderLayout.CENTER);
                textPanel.add(categoryLabel, BorderLayout.SOUTH);
            }
            icon.setBackground(VV.bgDarkColor);
            icon.setBorder(BorderFactory.createLineBorder(VV.bgDarkColor, VV.margin));
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
            setBackground(VV.mainColor);
//            titleLabel.setForeground(VV.mainTextColor);
//            categoryLabel.setForeground(VV.mainTextColor);
        } else { // when don't select
            setBackground(VV.bgLightColor);
//            titleLabel.setForeground(VV.mainTextColor);
//            categoryLabel.setForeground(VV.mainTextColor);
        }

        return this;
    }
}
