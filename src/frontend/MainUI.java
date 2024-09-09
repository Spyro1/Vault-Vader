package frontend;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {


    final int width = 1000, height = 600;

    public MainUI() {
        // Create main frame
//      JFrame frame = new JFrame("Vault Vader");
        ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
        setTitle("Vault Vader");
        setIconImage(lockIcon.getImage()); // Set status bar icon
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);

        JPanel headerPanel = new JPanel(); {
//          headerPanel.setLayout(new GridLayout(0, 2));
            headerPanel.setBackground(VV.mainColor);
            headerPanel.setBounds(0, 0, width, 40);
            headerPanel.setLayout(null);
            JLabel VaultVaderLabel = new JLabel("Vault Vader", JLabel.CENTER); {
                VaultVaderLabel.setForeground(VV.mainTextColor);
                VaultVaderLabel.setFont(new Font("Distant Galaxy", Font.BOLD, 30));
                VaultVaderLabel.setBounds(0, 0, 260, 40);
                VaultVaderLabel.setHorizontalAlignment(JLabel.CENTER);
                VaultVaderLabel.setVerticalAlignment(JLabel.CENTER);
                headerPanel.add(VaultVaderLabel);
            }
        }
        add(headerPanel);
        JPanel sidePanel = new JPanel(); {
            sidePanel.setBackground(VV.bgLightColor);
            sidePanel.setBounds(0, 40, 260, height-40);
            sidePanel.setLayout(null);
        }
        add(sidePanel);
        JPanel bodyPanel = new JPanel(); {
            bodyPanel.setBackground(VV.bgDarkColor);
            bodyPanel.setBounds(260, 40, width-260, height-40);
            bodyPanel.setLayout(null);
        }
        add(bodyPanel);
    }
}
