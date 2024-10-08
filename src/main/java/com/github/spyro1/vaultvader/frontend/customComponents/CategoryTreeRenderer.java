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
/*        @Override
        public Color getTextNonSelectionColor() {
            return VV.mainTextColor;
        }
        @Override
        public Color getBackgroundNonSelectionColor() {
            return VV.bgLightColor;
        }
        @Override
        public Color getTextSelectionColor(){
            return VV.mainTextColor;
        }
        @Override
        public Color getBackgroundSelectionColor() {
            return VV.mainColor;
        }*/

        @Override
        public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
            final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

//            final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
            this.setText(value.toString());
            return ret;
        }

}
