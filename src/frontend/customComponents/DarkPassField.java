package frontend.customComponents;

import frontend.VV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

public class DarkPassField extends JPasswordField {

    private String placeholder = "";
    private char defChar = getEchoChar();

    public DarkPassField() {
        super();
        setup();
    }
    public DarkPassField(String text, String placeholder) {
        super(text);
        this.placeholder = placeholder;
        setup();
    }
    public DarkPassField(String text, String placeholder, boolean underline) {
        super(text);
        this.placeholder = placeholder;
        setup();
        setUnderline(underline);
    }
    private void setup(){
        setForeground(VV.mainTextColor);
        setBackground(VV.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setCaretColor(VV.mainTextColor);
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
    }
    public void setUnderline(boolean underline) {
        if (underline)
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0, VV.mainTextColor), BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor)));
        else
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
    }
    public void showPassword(boolean shown){
        if (shown) {
            setEchoChar((char) 0);
        } else {
            setEchoChar(defChar);
        }
    }
}
