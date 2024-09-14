package frontend;

import backend.API;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class MainUI extends JFrame {

    final int width = 1000, height = 600; // Default  size of the window
    final int margin = 10;
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
                titleCenterPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
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
            JPanel centerPanel = new JPanel(new BorderLayout()); {
                centerPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin,margin,margin));
                centerPanel.setBackground(VV.bgDarkColor);
                DarkTextField searchField = new DarkTextField();
                centerPanel.add(searchField);
            }
            headerPanel.add(centerPanel);
            IconButton searchButton = new IconButton("", new ImageIcon("assets/white/search.png"));
            headerPanel.add(searchButton);
        }
        sidePanel = new JPanel(new BorderLayout()); {
            sidePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidePanel.setBackground(VV.bgLightColor);
            JPanel categoryLabelPanel = new JPanel(new BorderLayout()); {
                categoryLabelPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin*3, margin, margin*3));
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
                scrollPane.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
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
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            JPanel marginPanel = new JPanel(); {
                marginPanel.setLayout(new BoxLayout(marginPanel, BoxLayout.Y_AXIS));
                marginPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
                marginPanel.setOpaque(false);
                JPanel firstRow = new JPanel(new BorderLayout()); {
                    firstRow.setOpaque(false);
                    IconButton iconButton = new IconButton("", new ImageIcon("assets/white/picture.png"));
                    firstRow.add(iconButton, BorderLayout.WEST);
                    DarkTextField titleField = new DarkTextField();
                    firstRow.add(titleField, BorderLayout.CENTER);
                }
                marginPanel.add(firstRow);

            }
            contentPanel.add(marginPanel);
        }

        // title panel: Top-right corner
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = headerWeightX;
        gbc.weighty = headerWeightY;
        add(titlePanel, gbc);

        // header panel: Spanning across the screen (top row)
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1-headerWeightX;
        gbc.gridwidth = 2; // Span across two columns
        add(headerPanel, gbc);

        // side panel: Side panel below the first two (left side)
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = headerWeightX;
        gbc.weighty = contentWeightY;
        gbc.gridwidth = 1; // Reset width to 1
        add(sidePanel, gbc);

        // slider panel: Between panel 3 and panel 5 (middle)
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        gbc.weighty = contentWeightY;
        add(sliderPanel, gbc);

        // content panel: Below the first two panels (right side)
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.6;
        gbc.weighty = contentWeightY;
        add(contentPanel, gbc);
        // Set window visible
        setVisible(true);
    }

//    public void addObject(Component component, Container yourcontainer, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight){
//        gbc.gridx = gridx;
//        gbc.gridy = gridy;
//        gbc.gridwidth = gridwidth;
//        gbc.gridheight = gridheight;
//        layout.setConstraints(component, gbc);
//        yourcontainer.add(component);
//    }
}
