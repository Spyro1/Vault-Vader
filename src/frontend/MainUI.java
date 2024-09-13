package frontend;

import backend.API;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MainUI extends JFrame {

    final int width = 1000, height = 600; // Default  size of the window
    final double headerWeightY = 0.01, headerWeightX = 0.1;
    final double contentWeightY = 1-headerWeightY;
    JPanel titlePanel, headerPanel, sidePanel, sliderPanel, contentPanel; //, categoryPanel;
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


        titlePanel = new JPanel(new BorderLayout()); {
            JPanel titleCenterPanel = new JPanel(new BorderLayout()); {
                titleCenterPanel.setBackground(VV.bgDarkColor);
                titleCenterPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                JLabel VaultVaderLabel = new JLabel("Vault Vader", JLabel.CENTER); {
                    VaultVaderLabel.setForeground(VV.mainTextColor);
                    VaultVaderLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
                    VaultVaderLabel.setFont(new Font("Impact", Font.BOLD, 30));
                    titleCenterPanel.add(VaultVaderLabel);
                }
            }
            titlePanel.add(titleCenterPanel);
         }
        headerPanel = new JPanel(); {
            headerPanel.setBackground(VV.bgDarkColor);
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
            JPanel centerPanel = new JPanel();
            centerPanel.setBorder(BorderFactory.createEmptyBorder(10,20,10,15));
//            centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
            centerPanel.setLayout(new BorderLayout());
            centerPanel.setBackground(VV.bgDarkColor);
            headerPanel.add(centerPanel);
            JTextField searchField = new JTextField(); {
                searchField.setForeground(VV.mainTextColor);
                searchField.setBackground(VV.bgLightColor);
                searchField.setFont(new Font("Arial", Font.PLAIN, 20));
//                searchField.setOpaque(false);
                searchField.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
//                searchField.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white), "Keresés ", 0, 0, new Font("Arial", Font.BOLD, 12),  VV.mainTextColor));
            }
            centerPanel.add(searchField);
            JButton searchButton = new JButton("", new ImageIcon("assets/white/search.png")); {
                searchButton.setBackground(VV.bgDarkColor);
                searchButton.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            }
            headerPanel.add(searchButton);
        }
        sidePanel = new JPanel(new BorderLayout()); {
            sidePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidePanel.setBackground(VV.bgLightColor);
            JPanel categoryLabelPanel = new JPanel(new BorderLayout()); {
                categoryLabelPanel.setBorder(BorderFactory.createEmptyBorder(10,30,10,30));
                categoryLabelPanel.setOpaque(false);
                JLabel categoryLabel = new JLabel("Kategóriák"); {
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    categoryLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
                }
                categoryLabelPanel.add(categoryLabel);
            }
            sidePanel.add(categoryLabelPanel, BorderLayout.NORTH);
            DefaultMutableTreeNode baseCategory = new DefaultMutableTreeNode("Kategóriák");
            baseCategory.add(new DefaultMutableTreeNode(API.getCategoryList())); // GET data from API
            JTree categoryTree = new JTree(baseCategory);  {
                categoryTree.setOpaque(false);
                categoryTree.setFont(new Font("Arial", Font.PLAIN, 15));
                categoryTree.setForeground(VV.mainTextColor);
                categoryTree.setCellRenderer(new MyTreeRenderer());
                categoryTree.setRootVisible(false);

            }
            JScrollPane scrollPane = new JScrollPane(categoryTree); {
                scrollPane.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                scrollPane.setOpaque(false);
                scrollPane.setBackground(VV.bgLightColor);
                scrollPane.getViewport().setOpaque(false);
            }
            sidePanel.add(scrollPane);
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
        // Set window visible
        setVisible(true);
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
