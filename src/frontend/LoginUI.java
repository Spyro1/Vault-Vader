package frontend;

import backend.API;
import backend.User;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame /*implements ActionListener*/ {

    static public void main(String[] args){
        new LoginUI();
    }

    private JLabel titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginUI() {
        // === Essential frame setup ===
        setTitle("VaultVader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
        setIconImage(lockIcon.getImage()); // Set status bar icon
        // === Component setup ===
        InitMinimalistLoginUI();

        setVisible(true);
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
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setBackground(VV.bgDarkColor);

        // Title
        titleLabel = new JLabel("Vault Vader", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBackground(Color.BLUE);
        titleLabel.setForeground(VV.mainTextColor);
        titleLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        // Input fields
        usernameField = new JTextField();
        usernameField.setBorder(BorderFactory.createTitledBorder(null, "Felhasználónév ", 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
        usernameField.setForeground(VV.mainTextColor);
        usernameField.setOpaque(false);
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createTitledBorder(null, "Jelszó", 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
        passwordField.setForeground(VV.mainTextColor);
        passwordField.setOpaque(false);
        // Login Button
        loginButton = new JButton("Bejelentkezés");
        loginButton.addActionListener(e -> {
            try {
                System.out.println("Bejelentkezek!");
                JSONObject userData = new JSONObject();
                userData.put("username",  usernameField.getText());
                userData.put("password",  passwordField.getText());
                if (API.loginRequest(userData)){
                    dispose();
                }
                else{
                    throw new Exception("Helytelen felhasználónév vagy jelszó!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
        // Register Button
        registerButton = new JButton("Regisztráció");
        registerButton.addActionListener(e -> {System.out.println("Regisztrálok!");});
        // Create Panel for Components
        JPanel centerPanel = new JPanel(new GridLayout(5,  1, 15, 15));
        centerPanel.setBackground(VV.bgDarkColor);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));
        centerPanel.add(titleLabel);
        centerPanel.add(usernameField);
        centerPanel.add(passwordField);
        centerPanel.add(loginButton);
        centerPanel.add(registerButton);

        // Add Components to Frame
        add(centerPanel, BorderLayout.CENTER);
    }
}
