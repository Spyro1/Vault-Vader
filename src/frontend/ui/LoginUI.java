package frontend.ui;

import backend.API;
import frontend.VV;
import frontend.customComponents.DarkPassField;
import frontend.customComponents.DarkTextField;
import frontend.customComponents.IconButton;

import org.json.simple.JSONObject;
import javax.swing.*;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.awt.event.ItemEvent;

public class LoginUI extends JFrame /*implements ActionListener*/ {

    static public void main(String[] args){
        try{
            new LoginUI(); // TO RUN
//            new MainUI(); // TESTING
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private DarkTextField usernameField;
    private DarkPassField passwordField;

    public LoginUI() {
        // === Essential frame setup ===
        setTitle("Vault Vader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
        setIconImage(lockIcon.getImage()); // Set status bar icon
        // === Component setup ===
        initMinimalistLoginUI();

        setVisible(true);
    }

    private void initMinimalistLoginUI(){
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setBackground(VV.bgDarkColor);


        // Create Panel for Components
        JPanel centerPanel = new JPanel(new GridLayout(5,  1, 15, 15)); {
            centerPanel.setBackground(VV.bgDarkColor);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));
        }
        // Title
        JLabel titleLabel = new JLabel("Vault Vader", SwingConstants.CENTER);
        {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setBackground(Color.BLUE);
            titleLabel.setForeground(VV.mainTextColor);
            titleLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        }
        centerPanel.add(titleLabel);
        // Input fields
        usernameField = new DarkTextField("","Felhasználónév");
        centerPanel.add(usernameField);
        JPanel passwordPanel = new JPanel(new BorderLayout()); {
            passwordField = new DarkPassField("","Jelszó");
            passwordPanel.add(passwordField, BorderLayout.CENTER);
            JToggleButton showPasswordBox = new JToggleButton(); {
                showPasswordBox.setBackground(VV.bgLightColor);
                showPasswordBox.setUI(new MetalToggleButtonUI(){
                    @Override
                    protected Color getSelectColor() {
                        return VV.secondaryTextColor;
                    }
                });
                showPasswordBox.setToolTipText("Jelszó megjelenítése");
                showPasswordBox.setIcon(new ImageIcon("assets/white/eye-closed.png"));
                showPasswordBox.setBorder(null);
                showPasswordBox.addItemListener(e -> {
                    passwordField.showPassword(e.getStateChange() == ItemEvent.SELECTED);
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        showPasswordBox.setIcon(new ImageIcon("assets/white/eye.png"));
                        showPasswordBox.setToolTipText("Jelszó elrejtése");
                    } else {
                        showPasswordBox.setIcon(new ImageIcon("assets/white/eye-closed.png"));
                        showPasswordBox.setToolTipText("Jelszó megjelenítése");
                    }
                });
            }
            passwordPanel.add(showPasswordBox, BorderLayout.EAST);
        }
        centerPanel.add(passwordPanel);
        // Login Button
        IconButton loginButton = new IconButton("Bejelentkezés"); {
            loginButton.setIcon(new ImageIcon("assets/white/login.png"));
            loginButton.setBackground(VV.mainColor);
            loginButton.setForeground(VV.mainTextColor);

//            loginButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin * 3, VV.margin, VV.margin * 3));
            loginButton.addActionListener(_ -> {
                try {
                    // Create JSON object from username and password
                    JSONObject userData = userFiledsToJSON();
                    // Try login with user's date
                    if (API.loginRequest(userData)){
    //                    JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!", "Bejelentkezés", JOptionPane.INFORMATION_MESSAGE);
                        dispose(); // Successful login -> Close window
                    }
                    else{
                        throw new Exception("Helytelen felhasználónév vagy jelszó!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        centerPanel.add(loginButton);
        // Register Button
        IconButton registerButton = new IconButton("Regisztráció");
        {
            registerButton.setIcon(new ImageIcon("assets/white/user.png"));
            registerButton.setBackground(VV.secondaryColor);
            registerButton.setForeground(VV.mainTextColor);
            registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
//            registerButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin * 4, VV.margin, VV.margin * 4));
            registerButton.addActionListener(_ -> {
                try {
                    // Create JSON object from username and password
                    JSONObject userData = userFiledsToJSON();
//                    JSONObject userData;
                    // Ask for clarification
                    if (JOptionPane.showConfirmDialog(null, "Biztosan regisztrálsz egy új felhasználót?", "Regisztráció", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        // Try register with user's date
                        if (API.registerRequest(userData)){
                            JOptionPane.showMessageDialog(null, "Felhasználó sikeresen létrehozva!", "Regisztráció", JOptionPane.INFORMATION_MESSAGE); // Successful register -> Show a success dialog box
                        }
                        else{
                            throw new Exception("Nem sikerült létrehozni a felhasználót!");
                        }

                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
        centerPanel.add(registerButton);

        // Add Components to Frame
        add(centerPanel, BorderLayout.CENTER);

        getRootPane().setDefaultButton(loginButton);
    }

    private JSONObject userFiledsToJSON() throws Exception {
        JSONObject userData = new JSONObject();
        userData.put(API.USERNAME_KEY,  usernameField.getText());
        userData.put(API.PASSWORD_KEY, API.encryptData(passwordField.getText(), usernameField.getText()));
        if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {
            throw new Exception("Nincs minden szükséges mező kitöltve!");
        }
        return userData;
    }
}
