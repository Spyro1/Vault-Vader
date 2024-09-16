package frontend.ui;

import backend.API;
import frontend.VV;
import frontend.components.*;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainUI extends JFrame {

    final int width = 1000, height = 600; // Default  size of the window
    final int margin = 10;
    final double headerWeightY = 0.01, contentWeightY = 1- headerWeightY,
                 firsColWeight = 0.1, secondColWeight = 0.3, thirdColWeight = 0.6;
    JPanel titlePanel, searchPanel, headerPanel, sidePanel, sliderPanel, contentBackPanel, editorPanel; //, categoryPanel;
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // On closing actions
                try{
                    API.saveAllChanges();
                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
                e.getWindow().dispose();
            }
        });

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
        // SEARCH PANEL for the search bar
        searchPanel = new JPanel(new BorderLayout()); {
            searchPanel.setBackground(VV.bgDarkColor);
            JPanel centerPanel = new JPanel(new BorderLayout()); {
                centerPanel.setBackground(VV.bgLightColor);
                centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(margin, margin, margin, 0, VV.bgDarkColor), BorderFactory.createEmptyBorder(margin, margin, margin, margin)));
                DarkTextField searchField = new DarkTextField("","Keresés");
                IconButton searchButton = new IconButton("", new ImageIcon("assets/white/search.png")); {
                    searchButton.setToolTipText("Keresés");
                }
                centerPanel.add(searchField, BorderLayout.CENTER);
                centerPanel.add(searchButton, BorderLayout.EAST);
            }
            searchPanel.add(centerPanel);
        }
        // HEADER PANEL for the tool buttons
        headerPanel = new JPanel(new BorderLayout()); {
            headerPanel.setBackground(VV.bgDarkColor);
            JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); {
                centerPanel.setBackground(VV.bgLightColor);
                centerPanel.setBorder(BorderFactory.createMatteBorder(margin, 0, margin, margin, VV.bgDarkColor));
                IconButton saveButton = new IconButton("", new ImageIcon("assets/white/save.png")); {
                    saveButton.setToolTipText("Mentés");
                }
                centerPanel.add(saveButton);
                IconButton addNewItemButton = new IconButton("", new ImageIcon("assets/white/plus.png")); {
                    addNewItemButton.setToolTipText("Új bejegyzés");
                }
                centerPanel.add(addNewItemButton);
                IconButton logOutButton = new IconButton("", new ImageIcon("assets/white/logout.png")); {
                    logOutButton.setToolTipText("Kijelentkezés");
                }
                centerPanel.add(logOutButton);
            }
            headerPanel.add(centerPanel);
        }
        // SIDE PANEL for filtering for categories
        sidePanel = new JPanel(new BorderLayout()); {
            sidePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            sidePanel.setBackground(VV.bgLightColor);
            JPanel categoryLabelPanel = new JPanel(new BorderLayout()); {
                categoryLabelPanel.setBorder(BorderFactory.createMatteBorder(0, margin,0, margin, VV.bgDarkColor));
                categoryLabelPanel.setOpaque(false);
                JLabel categoryLabel = new JLabel("Kategóriák"); {
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    categoryLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin), BorderFactory.createMatteBorder(0,0,2,0, Color.white)));
                }
                categoryLabelPanel.add(categoryLabel);
            }
            sidePanel.add(categoryLabelPanel, BorderLayout.NORTH);
            categoryTree = new JTree(allItemCategory); {
                categoryTree.setOpaque(false);
                categoryTree.setFont(new Font("Arial", Font.PLAIN, 15));
                categoryTree.setForeground(VV.mainTextColor);
                categoryTree.setCellRenderer(new MyTreeRenderer());
//                categoryTree.setRootVisible(false);
            }
            refresh();
            JScrollPane scrollPane = new JScrollPane(categoryTree); {
                scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, margin,0, margin, VV.bgDarkColor), BorderFactory.createEmptyBorder(0, margin, margin, margin)));
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
                            json.put("category", categoryField.getText());
                            if (API.addNewCategory(json)) {
                                refresh();
                            }
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
                            json.put("category", categoryField.getText());
                            if (API.removeCategory(json)) {
                                refresh(); // Refresh category tree
                            }
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

        /* Setup GridBagLayout properties and add panels */ {

            // title panel: Top-right corner
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = firsColWeight;
            gbc.weighty = headerWeightY;
            add(titlePanel, gbc);

            // search panel: second top cell
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = secondColWeight;
            add(searchPanel, gbc);

            // header panel: third top cell
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.weightx = thirdColWeight;
            add(headerPanel, gbc);

            // side panel: Side panel below the first two (left side)
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = firsColWeight;
            gbc.weighty = contentWeightY;
            add(sidePanel, gbc);

            // slider panel: Between panel 3 and panel 5 (middle)
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.weightx = secondColWeight;
            add(sliderPanel, gbc);

            // content panel: Below the first two panels (right side)
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.weightx = thirdColWeight;
            add(contentBackPanel, gbc);
        }

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
        // Expand the root
        categoryTree.expandRow(0);
        // Reload Tree view
        DefaultTreeModel model = (DefaultTreeModel) categoryTree.getModel();
        model.reload();
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
