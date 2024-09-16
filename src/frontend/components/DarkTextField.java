package frontend.components;

import frontend.VV;

import javax.swing.*;
import java.awt.*;

public class DarkTextField extends JTextField {
    public DarkTextField() {
        super();
        setForeground(VV.mainTextColor);
        setBackground(VV.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        setCaretColor(VV.mainTextColor);
    }
    public DarkTextField(String text) {
        super(text);
        setForeground(VV.mainTextColor);
        setBackground(VV.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
    }
}
