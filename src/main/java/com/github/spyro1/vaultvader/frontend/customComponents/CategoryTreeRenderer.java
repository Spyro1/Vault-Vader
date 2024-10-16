package com.github.spyro1.vaultvader.frontend.customComponents;

import com.github.spyro1.vaultvader.frontend.UI;

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Objects;

public class CategoryTreeRenderer extends DefaultTreeCellRenderer {
    
    public CategoryTreeRenderer() {
        leafIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("label.png")));
        openIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("folder.png")));
        closedIcon = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("folder.png")));
        
        setTextSelectionColor(UI.mainTextColor);
        setBackgroundSelectionColor(UI.mainColor);
        setTextNonSelectionColor(UI.mainTextColor);
        setBackgroundNonSelectionColor(UI.bgLightColor);
        setBorderSelectionColor(null);
    }
    
    @Override
    public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
        final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        this.setText(value.toString());
        return ret;
    }
}
