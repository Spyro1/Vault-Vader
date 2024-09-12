package frontend;

import javax.swing.*;
import java.awt.*;

public class MainUI extends JFrame {

    final int width = 1000, height = 600;
    final double headerWeightY = 0.01, headerWeightX = 0.1;
    final double contentWeightY = 1-headerWeightY;
    JPanel titlePanel, headerPanel, sidePanel, sliderPanel, contentPanel;

    public MainUI() {
        // Create main frame
        setTitle("Vault Vader");
        setSize(width, height);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        ImageIcon lockIcon = new ImageIcon("assets/black/lock.png");
        setIconImage(lockIcon.getImage()); // Set status bar icon

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        titlePanel = new JPanel(); {
            titlePanel.setBackground(VV.mainColor);
            JLabel VaultVaderLabel = new JLabel("Vault Vader", JLabel.CENTER); {
                VaultVaderLabel.setForeground(VV.mainTextColor);
                VaultVaderLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
                VaultVaderLabel.setFont(new Font("Impact", Font.BOLD, 30));
//                VaultVaderLabel.setBounds(0, 0, 260, 40);
//                VaultVaderLabel.setHorizontalAlignment(JLabel.CENTER);
//                VaultVaderLabel.setVerticalAlignment(JLabel.CENTER);
                titlePanel.add(VaultVaderLabel);
            }
         }
        headerPanel = new JPanel(); {
            headerPanel.setBackground(VV.mainColor);
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
            JPanel centerPanel = new JPanel();
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10,15,15,15));
            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
            centerPanel.setBackground(VV.mainColor);
            headerPanel.add(centerPanel);
//            JLabel searchLabel = new JLabel("Keresés", JLabel.LEFT); {
//                searchLabel.setForeground(VV.mainTextColor);
////                searchLabel.setVerticalAlignment(JLabel.CENTER);
//            }
            JTextField searchField = new JTextField(); {
                searchField.setForeground(VV.mainTextColor);
                searchField.setFont(new Font("Arial", Font.PLAIN, 20));
                searchField.setOpaque(false);
                searchField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
//                searchField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white), "Keresés ", 0, 0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
            }
            centerPanel.add(searchField);
            JButton searchButton = new JButton("", new ImageIcon("assets/white/search.png")); {
//                searchButton.setForeground(VV.mainTextColor);
                searchButton.setBackground(VV.mainColor);
//                searchButton.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            }
            centerPanel.add(searchButton);
//            gbc.anchor = GridBagConstraints.LINE_START;
//            centerPanel.add(searchLabel, gbc);
//            gbc.anchor = GridBagConstraints.CENTER;
//            centerPanel.add(searchField, gbc);
        }
        sidePanel = new JPanel(); {
            sidePanel.setBackground(VV.bgLightColor);
        }
        sliderPanel = new JPanel(); {
            sliderPanel.setBackground(VV.bgDarkColor);
        }
        contentPanel = new JPanel(); {
            contentPanel.setBackground(VV.bgDarkColor);
        }

        // Panel 1: Top-right corner
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = headerWeightX;
        gbc.weighty = headerWeightY;
        gbc.fill = GridBagConstraints.BOTH;
        add(titlePanel, gbc);

        // Panel 2: Spanning across the screen (top row)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1-headerWeightX;
        gbc.gridwidth = 2; // Span across two columns
        add(headerPanel, gbc);

        // Panel 3: Side panel below the first two (left side)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = headerWeightX;
        gbc.weighty = contentWeightY;
        gbc.gridwidth = 1; // Reset width to 1
        add(sidePanel, gbc);

        // Panel 4: Between panel 3 and panel 5 (middle)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.weighty = contentWeightY;
        add(sliderPanel, gbc);

        // Panel 5: Below the first two panels (right side)
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.weighty = contentWeightY;
        add(contentPanel, gbc);
    }

    public void addObject(Component component, Container yourcontainer, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        layout.setConstraints(component, gbc);
        yourcontainer.add(component);
    }
}
