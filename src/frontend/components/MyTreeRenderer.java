package frontend.components;

import frontend.VV;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;

public class MyTreeRenderer extends DefaultTreeCellRenderer {

//        private final ImageIcon leaf = new ImageIcon("../assets/white/file.png");

        public MyTreeRenderer() {
                leafIcon = new ImageIcon("assets/white/label.png");
                openIcon = new ImageIcon("assets/white/folder.png");
                closedIcon = new ImageIcon("assets/white/folder.png");
        }
        @Override
        public Color getTextNonSelectionColor() {
            return VV.mainTextColor;
        }
        @Override
        public Color getBackgroundNonSelectionColor() {
            return VV.bgLightColor;
        }
        @Override
        public Color getTextSelectionColor(){
            return VV.bgDarkColor;
        }
        @Override
        public Color getBackgroundSelectionColor() {
            return VV.secondaryTextColor;
        }

        @Override
        public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean sel, final boolean expanded, final boolean leaf, final int row, final boolean hasFocus) {
            final Component ret = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

            final DefaultMutableTreeNode node = ((DefaultMutableTreeNode) (value));
            this.setText(value.toString());
            return ret;
        }

}
