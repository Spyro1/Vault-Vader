package frontend.components;

import frontend.VV;

import javax.swing.*;
import java.awt.*;

public class IconButton extends JButton {


    public IconButton() {
        super();
        setup();
    }
    public IconButton(Icon icon) {
        super(icon);
        setup();
    }
    public IconButton(String text) {
        super(text);
        setup();
    }
    public IconButton(String text, Icon icon) {
        super(text, icon);
        setup();
    }
    private void setup(){
//        setOpaque(false);
        setBackground(VV.bgDarkColor);
        setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
        setFont(new Font("Arial", Font.PLAIN, 20));
    }
}
