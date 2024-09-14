package frontend;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {
    final int margin = 10;

    public IconButton() {
        super();
        setBackground(VV.bgDarkColor);
        setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
    }
    public IconButton(Icon icon) {
        super(icon);
        setBackground(VV.bgDarkColor);
        setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
    }
    public IconButton(String text, Icon icon) {
        super(text, icon);
        setBackground(VV.bgDarkColor);
        setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
    }
}
