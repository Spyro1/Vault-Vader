package com.github.spyro1.vaultvader.frontend.ui;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.frontend.UI;
import com.github.spyro1.vaultvader.frontend.customComponents.DarkPassField;
import com.github.spyro1.vaultvader.frontend.customComponents.DarkTextField;
import com.github.spyro1.vaultvader.frontend.customComponents.IconButton;

import org.json.simple.JSONObject;
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;

public class LoginUI extends JFrame /*implements ActionListener*/ {

    private DarkTextField usernameField;
    private DarkPassField passwordField;
    private JToggleButton passwordShowToggler;

    public LoginUI() {
        // === Essential frame setup ===
        setTitle("Vault Vader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon lockIcon = new ImageIcon(this.getClass().getClassLoader().getResource("lock.png")); //"assets/black/lock.png");
        setIconImage(lockIcon.getImage()); // Set status bar icon
        // === Component setup ===
        initMinimalistLoginUI();

        setVisible(true);
    }

    private void initMinimalistLoginUI(){
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);
        setBackground(UI.bgDarkColor);

        // Create Panel for Components
        JPanel centerPanel = new JPanel(new GridLayout(5,  1, 15, 15)); {
            centerPanel.setBackground(UI.bgDarkColor);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));
        }
        // Title
        JLabel titleLabel = new JLabel("Vault Vader", SwingConstants.CENTER); {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
            titleLabel.setBackground(Color.BLUE);
            titleLabel.setForeground(UI.mainTextColor);
            titleLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        }
        centerPanel.add(titleLabel);
        // Input fields
        usernameField = new DarkTextField("","Felhasználónév"); {
            usernameField.setToolTipText("Írja be a felhasználónevét!");
        }
        centerPanel.add(usernameField);
        JPanel passwordPanel = new JPanel(new BorderLayout()); {
            passwordField = new DarkPassField("","Jelszó"); {
                passwordField.setToolTipText("Írja be a jelszavát!");
            }
            passwordPanel.add(passwordField, BorderLayout.CENTER);
            passwordShowToggler = new JToggleButton(); {
                passwordShowToggler.setBackground(UI.bgLightColor);
                passwordShowToggler.setUI(new MetalToggleButtonUI(){
                    @Override
                    protected Color getSelectColor() {
                        return UI.secondaryTextColor;
                    }
                });
                passwordShowToggler.setToolTipText("Jelszó megjelenítése");
                passwordShowToggler.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("eye-closed.png")));
                passwordShowToggler.setBorder(null);
                passwordShowToggler.addItemListener(this::togglePasswordButtonClcicked);
            }
            passwordPanel.add(passwordShowToggler, BorderLayout.EAST);
        }
        centerPanel.add(passwordPanel);
        // Login Button
        IconButton loginButton = new IconButton("Bejelentkezés"); {
            loginButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("login.png")));
            loginButton.setBackground(UI.mainColor);
            loginButton.setForeground(UI.mainTextColor);
            loginButton.setToolTipText("Bejelentkezés");
            loginButton.addActionListener(this::loginButtonClicked);
        }
        centerPanel.add(loginButton);
        // Register Button
        IconButton registerButton = new IconButton("Regisztráció"); {
            registerButton.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("user.png")));
            registerButton.setBackground(UI.secondaryColor);
            registerButton.setForeground(UI.mainTextColor);
            registerButton.setToolTipText("Regisztrációhoz írja be a használni kívánt felhasználónevét és jelszavát!");
            registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
            registerButton.addActionListener(this::registerButtonClicked);
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

    private void loginButtonClicked(ActionEvent e) {
        try {
            // Create JSON object from username and password
            JSONObject userData = userFiledsToJSON();
            // Try login with user's date
            if (API.loginRequest(userData)){
                dispose(); // Successful login -> Close window
            } else{
                throw new Exception("Helytelen felhasználónév vagy jelszó!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void registerButtonClicked(ActionEvent e) {
        try {
            // Create JSON object from username and password
            JSONObject userData = userFiledsToJSON();
            // Ask for clarification
            if (JOptionPane.showConfirmDialog(null, "Biztosan regisztrálsz egy új felhasználót?", "Regisztráció", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                // Try register with user's date
                if (API.registerRequest(userData)){
                    JOptionPane.showMessageDialog(null, "Felhasználó sikeresen létrehozva!", "Regisztráció", JOptionPane.INFORMATION_MESSAGE); // Successful register -> Show a success dialog box
                } else{
                    throw new Exception("Nem sikerült létrehozni a felhasználót!");
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void togglePasswordButtonClcicked(ItemEvent e) {
        passwordField.showPassword(e.getStateChange() == ItemEvent.SELECTED);
        if (e.getStateChange() == ItemEvent.SELECTED) {
            passwordShowToggler.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("eye.png")));
            passwordShowToggler.setToolTipText("Jelszó elrejtése");
        } else {
            passwordShowToggler.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("eye-closed.png")));
            passwordShowToggler.setToolTipText("Jelszó megjelenítése");
        }
    }

}
