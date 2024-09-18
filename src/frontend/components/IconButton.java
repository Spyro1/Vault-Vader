package frontend.components;

import frontend.VV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class IconButton extends JButton {

//    private Color hoverBackgroundColor;
//    private Color pressedBackgroundColor;

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
        setBackground(VV.bgLightColor);
        setForeground(VV.mainTextColor);
        setBorder(BorderFactory.createEmptyBorder(VV.margin/2, VV.margin/2, VV.margin/2, VV.margin/2));
        setFont(new Font("Arial", Font.PLAIN, 20));

//        hoverBackgroundColor = getBackground().brighter();
//        pressedBackgroundColor = getBackground().darker();

        setContentAreaFilled(false); // Sets to be able to paint enabled and hover background
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(getBackground().darker());
        } else if (getModel().isRollover()) {
            g.setColor(getBackground().brighter());
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }
}
