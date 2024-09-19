package frontend.customComponents;

import frontend.VV;

import javax.swing.*;
import java.awt.*;

public class DarkTextField extends JTextField {

    private String placeholder = "";
    public DarkTextField() {
        super();
        setup();
    }
    public DarkTextField(String text, String placeholder) {
        super(text);
        this.placeholder = placeholder;
        setup();
    }
    public DarkTextField(String text, String placeholder, boolean underline) {
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
//        setForeground(VV.secondaryTextColor);
//        setText(placeholder);

//        addFocusListener(new FocusListener() {
//            @Override
//            public void focusGained(FocusEvent e) {
//                if (getText().equals(placeholder)) {
//                    setText("");
//                    setForeground(VV.mainTextColor);
//                }
//            }
//            @Override
//            public void focusLost(FocusEvent e) {
//                if (getText().isEmpty()) {
//                    setForeground(VV.secondaryTextColor);
//                    setText(placeholder);
//                }
//            }
//        });
    }
    public void setUnderline(boolean underline) {
        if (underline)
            setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0, VV.mainTextColor), BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor)));
        else
            setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
    }
}
