package frontend.components;

import frontend.VV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class DarkTextField extends JTextField {
    public DarkTextField() {
        super();
        setup("");
    }
    public DarkTextField(String text, String placeholder) {
        super(text);
        setup(placeholder);

    }
    public void setup(String placeholder){
        setForeground(VV.mainTextColor);
        setBackground(VV.bgLightColor);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), placeholder, 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
        setCaretColor(VV.mainTextColor);
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
}
