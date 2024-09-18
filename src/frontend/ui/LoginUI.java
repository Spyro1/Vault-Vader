package frontend.ui;

import backend.API;
import frontend.VV;
import frontend.components.DarkTextField;
import frontend.components.IconButton;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalToggleButtonUI;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginUI extends JFrame /*implements ActionListener*/ {

    static public void main(String[] args){
        try{
            new LoginUI(); // TO RUN
//            new MainUI(); // TESTING
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JLabel titleLabel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private IconButton loginButton;
    private IconButton registerButton;

    public LoginUI() {
        // === Essential frame setup ===
        setTitle("Vault Vader");
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


        // Create Panel for Components
        JPanel centerPanel = new JPanel(new GridLayout(5,  1, 15, 15)); {
            centerPanel.setBackground(VV.bgDarkColor);
            centerPanel.setBorder(BorderFactory.createEmptyBorder(0,15,15,15));
        }
        // Title
        titleLabel = new JLabel("Vault Vader", SwingConstants.CENTER); {
            titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
            titleLabel.setBackground(Color.BLUE);
            titleLabel.setForeground(VV.mainTextColor);
            titleLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
        }
        centerPanel.add(titleLabel);
        // Input fields
        usernameField = new JTextField(); {
            usernameField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Felhasználónév ", 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
            usernameField.setForeground(VV.mainTextColor);
            usernameField.setCaretColor(VV.mainTextColor);
            usernameField.setBackground(VV.bgLightColor);
            usernameField.setFont(new Font("Arial", Font.PLAIN, 15));
//            usernameField.setOpaque(false);
        }
        centerPanel.add(usernameField);
        JPanel passwordPanel = new JPanel(new BorderLayout()); {
            passwordField = new JPasswordField(); {
                passwordField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Jelszó", 0,0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
                passwordField.setForeground(VV.mainTextColor);
                passwordField.setCaretColor(VV.mainTextColor);
                passwordField.setBackground(VV.bgLightColor);
                passwordField.setFont(new Font("Arial", Font.PLAIN, 15));
            }
            passwordPanel.add(passwordField, BorderLayout.CENTER);
            JToggleButton showPasswordBox = new JToggleButton(); {
//                showPasswordBox.setOpaque(true);
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
                char defChar = passwordField.getEchoChar();
                showPasswordBox.addItemListener(new ItemListener() {
                    @Override
                    public void itemStateChanged(ItemEvent e) {
                        if (e.getStateChange() == ItemEvent.SELECTED) {
                            passwordField.setEchoChar((char) 0);
                            showPasswordBox.setIcon(new ImageIcon("assets/white/eye.png"));
                            showPasswordBox.setToolTipText("Jelszó elrejtése");
                        } else {
                            passwordField.setEchoChar(defChar);
                            showPasswordBox.setIcon(new ImageIcon("assets/white/eye-closed.png"));
                            showPasswordBox.setToolTipText("Jelszó megjelenítése");
                        }
                    }
                });
            }
            passwordPanel.add(showPasswordBox, BorderLayout.EAST);
        }
        centerPanel.add(passwordPanel);
        // Login Button
        loginButton = new IconButton("Bejelentkezés"); {
            loginButton.setIcon(new ImageIcon("assets/white/login.png"));
            loginButton.setBackground(VV.mainColor);
            loginButton.setForeground(VV.mainTextColor);

//            loginButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin * 3, VV.margin, VV.margin * 3));
            loginButton.addActionListener(e -> {
                try {
                    // Create JSON object from username and password
                    JSONObject userData = new JSONObject();
                    userData.put("username",  usernameField.getText());
                    userData.put("password",  passwordField.getText());
                    if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                        throw new Exception("Nincs minden szükséges mező kitöltve!");
                    }
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
        registerButton = new IconButton("Regisztráció"); {
            registerButton.setIcon(new ImageIcon("assets/white/user.png"));
            registerButton.setBackground(VV.secondaryColor);
            registerButton.setForeground(VV.mainTextColor);
            registerButton.setFont(new Font("Arial", Font.PLAIN, 16));
//            registerButton.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin * 4, VV.margin, VV.margin * 4));
            registerButton.addActionListener(e -> {
                try {
                    // Create JSON object from username and password
                    JSONObject userData = new JSONObject();
                    userData.put("username",  usernameField.getText());
                    userData.put("password",  passwordField.getText());
                    if (usernameField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                        throw new Exception("Nincs minden szükséges mező kitöltve!");
                    }
                    // Ask for clarification
                    if (JOptionPane.showConfirmDialog(null, "Biztosan regisztrálsz egy új felhasználót?", "Regisztráció", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
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
}
