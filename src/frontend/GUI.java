package frontend;

import javax.swing.*;
import java.awt.*;

public class GUI {
    final Color mainColor = new Color(10,0,100);
    final Color secondaryColor = new Color(70,177,255);
    final Color bgLightColor = new Color(85,80,100);
    final Color bgDarkColor = new Color(24,31,36);
    final Color mainTextColor = new Color(230,230,230);
    final Color secondaryTextColor = new Color(170,170,170);

    final int width = 1000, height = 600;

    public GUI() {
        // Create main frame
        JFrame frame = new JFrame("Vault Vader"); {
            ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
            frame.setIconImage(lockIcon.getImage()); // Set status bar icon
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(width, height);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.setVisible(true);

            JPanel headerPanel = new JPanel(); {
//                headerPanel.setLayout(new GridLayout(0, 2));
                headerPanel.setBackground(mainColor);
                headerPanel.setBounds(0, 0, width, 40);
                headerPanel.setLayout(null);
                JLabel VaultVaderLabel = new JLabel("Vault Vader", JLabel.CENTER); {
                    VaultVaderLabel.setForeground(mainTextColor);
                    VaultVaderLabel.setFont(new Font("Distant Galaxy", Font.BOLD, 30));
                    VaultVaderLabel.setBounds(0, 0, 260, 40);
                    VaultVaderLabel.setHorizontalAlignment(JLabel.CENTER);
                    VaultVaderLabel.setVerticalAlignment(JLabel.CENTER);
                    headerPanel.add(VaultVaderLabel);
                }
            }
            frame.add(headerPanel);
            JPanel sidePanel = new JPanel(); {
                sidePanel.setBackground(bgLightColor);
                sidePanel.setBounds(0, 40, 260, height-40);
                sidePanel.setLayout(null);
            }
            frame.add(sidePanel);
            JPanel bodyPanel = new JPanel(); {
                bodyPanel.setBackground(bgDarkColor);
                bodyPanel.setBounds(260, 40, width-260, height-40);
                bodyPanel.setLayout(null);
            }
            frame.add(bodyPanel);
        }
    }
}
