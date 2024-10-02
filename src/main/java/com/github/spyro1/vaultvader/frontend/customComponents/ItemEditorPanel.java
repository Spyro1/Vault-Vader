package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.backend.Field;
import com.github.spyro1.vaultvader.backend.FieldType;
import com.github.spyro1.vaultvader.backend.Item;
import com.github.spyro1.vaultvader.frontend.UI;
import com.github.spyro1.vaultvader.frontend.ui.MainUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;

public class ItemEditorPanel extends JPanel {

    JPanel titleRow;
    IconButton iconButton;
    DarkTextField titleField;
    IconButton addNewFieldButton;

    MainUI window;
    Item displayedItem;

    public LinkedList<FieldPanel> fieldsList = new LinkedList<>();

    GridBagLayout gbl = new GridBagLayout();
    GridBagConstraints gbc = new GridBagConstraints();
    int gridY = 0;

    public ItemEditorPanel(MainUI window) {
        setLayout(gbl);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weighty = 0.01;
        gbc.weightx = 1;
        setBackground(UI.bgLightColor);
        titleRow = new JPanel(new BorderLayout()); {
            titleRow.setOpaque(false);
            titleRow.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
            iconButton = new IconButton("", "picture.png"); {
                iconButton.setToolTipText("Ikon hozzáadása");
                iconButton.setBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin));
                iconButton.addActionListener(this::iconSelectorButtonClicked);
            }
            titleRow.add(iconButton, BorderLayout.WEST);
            titleField = new DarkTextField("", "Bejegyzés címe", true);
            titleRow.add(titleField, BorderLayout.CENTER);
            // TODO: json ne legyen null teszteket írni
        }
        addNewFieldButton = new IconButton("Mező hozzáadása", "plus.png"); {
            addNewFieldButton.addActionListener(this::addNewFieldButtonClicked);
        }
    }

    public void hidePanel() {
        this.setVisible(false);
    }

    public void displayItem(Item displayedItem) {
        this.displayedItem = displayedItem;
        removeAll(); // clears panel
        gbc.weighty = 0.01;
        gbc.gridy = gridY++;
        add(titleRow, gbc);
        try {
            if (displayedItem != null) {
                iconButton.setIcon(displayedItem.getIcon());
                titleField.setText(displayedItem.getTitle());
                for (int i = 0; i < displayedItem.getFields().size(); i++) {
                    String fieldName = displayedItem.getFields().get(i).getFieldName();
                    String text = displayedItem.getFields().get(i).getValue();
                    FieldPanel fp = switch (displayedItem.getFields().get(i).getType()) {
                        case TEXT -> new FieldPanel(new DarkTextField(text, displayedItem.getFields().get(i).getFieldName(), true));
                        case PASS -> new FieldPanel(new DarkPassField(text, fieldName, true));
                        case CATEGORY -> new FieldPanel(new DarkComboField((new String[]{"elso", "masodik", "harmadik"}), fieldName, true));
                        case null, default -> new FieldPanel(new DarkTextField(text, fieldName, true));
                    };
                    fieldsList.add(fp);
                    gbc.gridy = gridY++;
                    add(fp, gbc);
                }
            }
        } catch (Exception e){
            System.err.println("ERROR/ItemEditorPanel/ " + e);
        }

        gbc.gridy = gridY++;
        add(addNewFieldButton, gbc);
        JPanel fillerPanel = new JPanel(); {
            fillerPanel.setOpaque(false);
        }
        gbc.weighty = 0.9;
        gbc.gridy = gridY++;
        add(fillerPanel, gbc);
        validate();
        setVisible(true);
    }

    // == Click events ==

    private void iconSelectorButtonClicked(ActionEvent actionEvent) {
        FileDialog fd = new FileDialog(window);
        fd.setDirectory("C:\\");
        fd.setFile("*.png|*.jpg|*.jpeg|*.gif");
        fd.setVisible(true);
        String filename = fd.getFile();
        if (filename == null)
            System.out.println("You cancelled the choice");
        else{
            displayedItem.setIcon(new ImageIcon(filename, filename));
            System.out.println("You chose " + filename);
            displayItem(displayedItem); // refresh
        }
    }

    private void addNewFieldButtonClicked(ActionEvent actionEvent) {
        JPopupMenu pm = new JPopupMenu(); {
            pm.setBackground(UI.bgDarkColor);
            pm.setForeground(UI.mainTextColor);
//            pm.setBorder(BorderFactory.createLineBorder(UI.secondaryTextColor));
            pm.setBorder(BorderFactory.createEmptyBorder());
            JMenuItem mi1 = new JMenuItem("Új szöveg mező"); {
                mi1.setForeground(UI.mainTextColor);
                mi1.setOpaque(false);
                mi1.addActionListener(_ -> popupMenuItemClicked(FieldType.TEXT));
            }
            JMenuItem mi2 = new JMenuItem("Új jelszó mező"); {
                mi2.setForeground(UI.mainTextColor);
                mi2.setOpaque(false);
                mi2.addActionListener(_ -> popupMenuItemClicked(FieldType.PASS));
            }
            pm.add(mi1);
            pm.add(mi2);
        }
        // Show popupmenu at buttons position
        Component source = (Component)actionEvent.getSource();
        Dimension size = source.getSize();
        int xPos = ((size.width - pm.getPreferredSize().width) / 2);
        int yPos = size.height;
        pm.show(source, xPos, yPos);
    }

    private void popupMenuItemClicked(FieldType chosenType) {
        String fieldName = JOptionPane.showInputDialog(this,"Írja be az új mező nevét!", "Új mező", JOptionPane.QUESTION_MESSAGE);
        if (fieldName != null && !fieldName.isBlank()) {
            displayItem(API.getTemporalItem().addField(new Field(fieldName, "", chosenType)));
        }
    }
}
