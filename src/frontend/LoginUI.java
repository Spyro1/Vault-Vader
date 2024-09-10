package frontend;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginUI extends JFrame {

    public LoginUI() {
        // === Essential frame settings ===
        setTitle("Vault Vader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setVisible(true);
//        setLayout(null);
        // === Extra settings ===
        ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
        setIconImage(lockIcon.getImage()); // Set status bar icon

        InitMinimalistLoginUI();

    }
    private void InitAdvancedLoginUI(){
        setLayout(new BorderLayout());
        // === Initialize Components ===
        JPanel titlePanel = new JPanel(); {
            titlePanel.setBackground(VV.mainColor);
            titlePanel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
            titlePanel.setBounds(0,0,this.getWidth(),40);
        }
        add(titlePanel, BorderLayout.NORTH);

        JPanel backPanel= new JPanel(); {
            backPanel.setBackground(VV.bgDarkColor);
            backPanel.setLayout(new BoxLayout(backPanel, BoxLayout.Y_AXIS));
        }
        add(backPanel, BorderLayout.CENTER);

        JLabel titleLabel = new JLabel("Vault Vader", JLabel.CENTER);
        {
            titleLabel.setForeground(VV.mainTextColor);
            titleLabel.setFont(new Font("Impact", Font.PLAIN, 30));
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setVerticalAlignment(JLabel.CENTER);
        }
        titlePanel.add(titleLabel);
        JLabel usernameLabel = new JLabel("Felhasználónév", JLabel.CENTER); {
            usernameLabel.setForeground(VV.secondaryTextColor);
//            usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
            usernameLabel.setBounds(100, 90, 200, 20);
//            usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        backPanel.add(usernameLabel);
        JTextField usernameField = new JTextField(); {
            usernameField.setForeground(VV.mainTextColor);
//            usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
//            usernameField.setHorizontalAlignment(JTextField.CENTER);
//            usernameField.setBounds(100, 60, 200, 30);
//            usernameField.setOpaque(false);
//            usernameField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        }
        backPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Jelszó", JLabel.CENTER); {
            passwordLabel.setForeground(VV.secondaryTextColor);
//            passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
//            passwordLabel.setBounds(100, 160, 200, 20);
//            passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        }
        backPanel.add(passwordLabel);
        JPasswordField passwordField = new JPasswordField(); {
            passwordField.setForeground(VV.mainTextColor);
//            passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
//            passwordField.setHorizontalAlignment(JTextField.CENTER);
//            passwordField.setBounds(100, 130, 200, 30);
//            passwordField.setOpaque(false);
//            passwordField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        }
        backPanel.add(passwordField);
        JButton loginButton = new JButton("Bejelentkezés"); {
            loginButton.setForeground(VV.mainTextColor);
            loginButton.setBackground(VV.secondaryColor);
//            loginButton.setFont(new Font("Arial", Font.PLAIN, 20));
//            loginButton.setBounds(120, 200, 160, 30);
//            loginButton.setHorizontalAlignment(JLabel.CENTER);
//            loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        }
        backPanel.add(loginButton);
    }
    private void InitMinimalistLoginUI(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Vault Vader", JLabel.CENTER); {
//            titleLabel.setBounds(0,0,getWidth(),40);
        }
        add(titleLabel);
        JTextField usernameField = new JTextField(); {
//            usernameField.setBounds(0, 50, getWidth(), 40);
            usernameField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Felhasználónév"), BorderFactory.createEmptyBorder(8,8,8,8)));
        }
        add(usernameField);
        JPasswordField passwordField = new JPasswordField(); {
//            passwordField.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Jelszó"), BorderFactory.createEmptyBorder(8,8,8,8)));
//            passwordField.setBounds(0, 80, getWidth(), 40);
            passwordField.setBorder(BorderFactory.createTitledBorder("Jelszó"));
        }
        add(passwordField);
        JButton loginButton = new JButton("Belépés"); {
//            loginButton.setBounds(0, 110, getWidth(), 40);
        }
        loginButton.addActionListener(e -> {});
        add(loginButton);

    }
}
