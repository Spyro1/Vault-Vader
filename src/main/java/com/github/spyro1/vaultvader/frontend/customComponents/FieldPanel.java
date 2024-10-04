package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.api.JSONSerializable;
import com.github.spyro1.vaultvader.frontend.UI;
import org.json.simple.JSONObject;

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

    /**
     * @JSONkeys "fieldName", "type", "vale"
     * @return the value of the field which implements the JSONSerializable interface
     */
    public JSONObject getFieldValue(){
//        if (this.dataField.getClass() == DarkTextField.class) {
//            return ((DarkTextField)this.dataField).toJSON();
//        } else if (this.dataField.getClass() == DarkPassField.class){
//            return ((DarkPassField)this.dataField).toJSON();
//        } else if (this.dataField.getClass() == DarkComboField.class){
//            return ((DarkComboField)this.dataField).toJSON();
//        }
        return ((JSONSerializable)(this.dataField)).toJSON();
    }

    private void moreOptionsButtonClicked(ActionEvent actionEvent) {
        DarkPopupMenuItem deleteFieldMenuItem = new DarkPopupMenuItem("Mező törlése", this::deleteThisFieldMenuItemClicked);
        DarkPopupMenuItem renameFieldMenuItem = new DarkPopupMenuItem("Mező átnevezése", this::renameThisFieldMenuItemClicked);
        DarkPopupMenu moreOptions = new DarkPopupMenu(deleteFieldMenuItem, renameFieldMenuItem);
        moreOptions.show(actionEvent);
//
//        Component source = (Component)actionEvent.getSource();
//        Dimension size = source.getSize();
//        int xPos = ((size.width /*- moreOptions.getPreferredSize().width) / 2*/));
//        int yPos = 0; //size.height / 2;
//        moreOptions.show(source, xPos, yPos);
    }

    private void renameThisFieldMenuItemClicked(ActionEvent e) {

    }

    private void deleteThisFieldMenuItemClicked(ActionEvent e) {

    }
}
