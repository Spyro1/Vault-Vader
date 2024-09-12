package frontend;

import backend.API;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;

public class LoginUI extends JFrame /*implements ActionListener*/ {

    static public void main(String[] args){
//        new LoginUI(); // TO RUN
        new MainUI(); // TESTING
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
                // Create JSON object from username and password
                JSONObject userData = new JSONObject();
                userData.put("username",  usernameField.getText());
                userData.put("password",  passwordField.getText());
                // Try login with user's date
                if (API.loginRequest(userData)){
                    dispose(); // Successful login -> Close window
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
        registerButton.addActionListener(e -> {
            try {
                // Create JSON object from username and password
                JSONObject userData = new JSONObject();
                userData.put("username",  usernameField.getText());
                userData.put("password",  passwordField.getText());
                // Ask for clarification
                if (JOptionPane.showConfirmDialog(null, "Biztosan regisztrálsz egy új felhasználót?") == JOptionPane.YES_OPTION){
                    // Try register with user's date
                    if (API.registerRequest(userData)){
                        JOptionPane.showMessageDialog(null, "Felhasználó sikeresen létrehozva!"); // Successful register -> Show a success dialog box
                    }
                    else{
                        throw new Exception("Nem sikerült létrehozni a felhasználót!");
                    }

                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
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
