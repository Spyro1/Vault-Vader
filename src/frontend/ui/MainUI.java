package frontend.ui;

import backend.API;
import frontend.VV;
import frontend.components.*;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;

public class MainUI extends JFrame {

    final int width = 1000, height = 600; // Default  size of the window
    final int margin = 10;
    final double headerWeightY = 0.01, headerWeightX = 0.1;
    final double contentWeightY = 1-headerWeightY;
    JPanel titlePanel, headerPanel, sidePanel, sliderPanel, contentBackPanel, editorPanel; //, categoryPanel;
    JTree categoryTree;
    DefaultMutableTreeNode allItemCategory = new DefaultMutableTreeNode("Minden bejegyzés");

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

        // TITLE PANEL for displaying the app name
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
        // HEADER PANEL for the search bar
        headerPanel = new JPanel(); {
            headerPanel.setBackground(VV.bgDarkColor);
            headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.X_AXIS));
            JPanel centerPanel = new JPanel(new BorderLayout()); {
                centerPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin,margin,margin));
                centerPanel.setBackground(VV.bgDarkColor);
                DarkTextField searchField = new DarkTextField("","Keresés");
                centerPanel.add(searchField);
            }
            headerPanel.add(centerPanel);
            IconButton searchButton = new IconButton("", new ImageIcon("assets/white/search.png"));
            searchButton.grabFocus();
            headerPanel.add(searchButton);
        }
        // SIDE PANEL for filtering for categories
        sidePanel = new JPanel(new BorderLayout()); {
            sidePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidePanel.setBackground(VV.bgLightColor);
            JPanel categoryLabelPanel = new JPanel(new BorderLayout()); {
                categoryLabelPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin*3, margin, margin*3));
                categoryLabelPanel.setOpaque(false);
                JLabel categoryLabel = new JLabel("Kategóriák"); {
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    categoryLabel.setBorder(BorderFactory.createMatteBorder(0,0,2,0, Color.white));
                }
                categoryLabelPanel.add(categoryLabel);
            }
            sidePanel.add(categoryLabelPanel, BorderLayout.NORTH);
            // Add base category and sub categories
//            DefaultMutableTreeNode baseCategory = new DefaultMutableTreeNode("Minden bejegyzés");
//            ArrayList<String> categories = API.getCategoryList(); // GET data from API
//            for (String category : categories) {
//                baseCategory.add(new DefaultMutableTreeNode(category));
//            }
            categoryTree = new JTree(allItemCategory); {
                categoryTree.setOpaque(false);
                categoryTree.setFont(new Font("Arial", Font.PLAIN, 15));
                categoryTree.setForeground(VV.mainTextColor);
                categoryTree.setCellRenderer(new MyTreeRenderer());
//                categoryTree.setRootVisible(false);
            }
            refresh();
            JScrollPane scrollPane = new JScrollPane(categoryTree); {
                scrollPane.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
                scrollPane.setOpaque(false);
                scrollPane.setBackground(VV.bgLightColor);
                scrollPane.getViewport().setOpaque(false);
            }
            sidePanel.add(scrollPane, BorderLayout.CENTER);
            JPanel categoryEditorToolPanel = new JPanel(); {
                categoryEditorToolPanel.setLayout(new BorderLayout());
                categoryEditorToolPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
//                categoryEditorToolPanel.setOpaque(false);
                categoryEditorToolPanel.setBackground(VV.bgDarkColor);
                DarkTextField categoryField = new DarkTextField("","Kategória"); {
                    categoryField.setFont(new Font("Arial", Font.PLAIN, 15));
                }
                IconButton addCategoryButton = new IconButton("", new ImageIcon("assets/white/plus.png")); {
                    addCategoryButton.setOpaque(false);
                    addCategoryButton.setBackground(VV.bgLightColor);
                    addCategoryButton.addActionListener(event -> {
                        try {
                            JSONObject json = new JSONObject();
                            json.put("caregory", categoryField.getText());
                            API.addNewCategory(json);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
                IconButton removeCategoryButton = new IconButton("", new ImageIcon("assets/white/minus.png")); {
                    removeCategoryButton.setOpaque(false);
                    removeCategoryButton.setBackground(VV.bgLightColor);
                    removeCategoryButton.addActionListener(event -> {
                        try {
                            JSONObject json = new JSONObject();
                            json.put("caregory", categoryField.getText());
                            API.removeCategory(json);
                        } catch (Exception e) {
                            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                }
                categoryEditorToolPanel.add(addCategoryButton, BorderLayout.WEST);
                categoryEditorToolPanel.add(categoryField, BorderLayout.CENTER);
                categoryEditorToolPanel.add(removeCategoryButton, BorderLayout.EAST);
            }
            sidePanel.add(categoryEditorToolPanel, BorderLayout.SOUTH);
        }
        // SLIDER PANEL for displaying every
        sliderPanel = new JPanel(); {
            sliderPanel.setBackground(VV.bgDarkColor);
        }
        // EDITOR PANEL for editing items
        contentBackPanel = new JPanel(); {
            contentBackPanel.setBackground(VV.bgDarkColor);
            contentBackPanel.setLayout(new BoxLayout(contentBackPanel, BoxLayout.Y_AXIS));
            editorPanel = new JPanel(); {
                editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
                editorPanel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
                editorPanel.setOpaque(false);
//                JPanel titleRow = new JPanel(new BorderLayout()); {
//                    titleRow.setOpaque(false);
//                    IconButton iconButton = new IconButton("", new ImageIcon("assets/white/picture.png"));
//                    titleRow.add(iconButton, BorderLayout.WEST);
//                    DarkTextField titleField = new DarkTextField();
//                    titleRow.add(titleField, BorderLayout.CENTER);
//                }
//                editorPanel.add(titleRow);
            }
            contentBackPanel.add(editorPanel);
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
        add(contentBackPanel, gbc);
        // Set window visible
        setVisible(true);
    }

    public void refresh(){
        // Refresh category tree data
        allItemCategory.removeAllChildren();
        ArrayList<String> categories = API.getCategoryList(); // GET data from API
        for (String category : categories) {
            allItemCategory.add(new DefaultMutableTreeNode(category));
        }
        categoryTree.expandRow(0);
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
