package frontend;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class LoginUI extends JFrame {
    public LoginUI() {
        ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
        setTitle("Vault Vader");
        setIconImage(lockIcon.getImage()); // Set status bar icon
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLayout(null);
        setVisible(true);

        JPanel headerPanel = new JPanel(); {
            headerPanel.setBackground(VV.mainColor);
            headerPanel.setBounds(0, 0, 400, 50);
            headerPanel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
            JLabel VaultVaderLabel = new JLabel("Vault Vader", JLabel.CENTER); {
                VaultVaderLabel.setForeground(VV.mainTextColor);
                VaultVaderLabel.setFont(new Font("Impact", Font.BOLD, 30));
                VaultVaderLabel.setBounds(0, 0, 400, 50);
                VaultVaderLabel.setHorizontalAlignment(JLabel.CENTER);
                VaultVaderLabel.setVerticalAlignment(JLabel.CENTER);
                headerPanel.add(VaultVaderLabel);
            }
        }
        add(headerPanel);
        JPanel buttonPanel = new JPanel(); {
            buttonPanel.setBackground(VV.bgDarkColor);
            buttonPanel.setBounds(0, 50, 400, 350);
            buttonPanel.setLayout(null);
            JTextField usernameField = new JTextField(); {
                usernameField.setForeground(VV.mainTextColor);
                usernameField.setFont(new Font("Arial", Font.PLAIN, 20));
                usernameField.setHorizontalAlignment(JTextField.CENTER);
                usernameField.setBounds(100, 60, 200, 30);
                usernameField.setOpaque(false);
                usernameField.setToolTipText("Felhasználónév");
                usernameField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
            }
            buttonPanel.add(usernameField);
            JLabel usernameLabel = new JLabel("Felhasználónév", JLabel.CENTER); {
                usernameLabel.setForeground(VV.secondaryTextColor);
                usernameLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                usernameLabel.setBounds(100, 90, 200, 20);
                usernameLabel.setHorizontalAlignment(JLabel.CENTER);
            }
            buttonPanel.add(usernameLabel);
            JTextField passwordField = new JTextField(); {
                passwordField.setForeground(VV.mainTextColor);
                passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
                passwordField.setHorizontalAlignment(JTextField.CENTER);
                passwordField.setBounds(100, 130, 200, 30);
                passwordField.setOpaque(false);
                passwordField.setToolTipText("Jelszó");
                passwordField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
            }
            buttonPanel.add(passwordField);
            JLabel passwordLabel = new JLabel("Jelszó", JLabel.CENTER); {
                passwordLabel.setForeground(VV.secondaryTextColor);
                passwordLabel.setFont(new Font("Arial", Font.PLAIN, 15));
                passwordLabel.setBounds(100, 160, 200, 20);
                passwordLabel.setHorizontalAlignment(JLabel.CENTER);
            }
            buttonPanel.add(passwordLabel);
            JButton loginButton = new JButton("Bejelentkezés"); {
                loginButton.setForeground(VV.bgLightColor);
                loginButton.setBackground(VV.secondaryColor);
                loginButton.setFont(new Font("Arial", Font.PLAIN, 25));
                loginButton.setBounds(120, 200, 160, 30);
                loginButton.setHorizontalAlignment(JLabel.CENTER);
            }
            buttonPanel.add(loginButton);

        }
        add(buttonPanel);
    }
}
