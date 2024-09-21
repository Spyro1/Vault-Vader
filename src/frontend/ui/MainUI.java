package frontend.ui;

import backend.API;
import backend.item.Item;
import frontend.VV;
import frontend.customComponents.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class MainUI extends JFrame {

    final int width = 1000, height = 600; // Default  size of the window
//    final int margin = 10;
    final double headerWeightY = 0.01, contentWeightY = 1- headerWeightY,
                 firsColWeight = 0.1, secondColWeight = 0.4, thirdColWeight = 0.5;
    JPanel titlePanel, searchPanel, headerPanel, sidePanel, sliderPanel, contentBackPanel, editorPanel; //, categoryPanel;
    JTree categoryTree;
    JList<Item> itemJList;
    int selectedItemIndex = -1, nextSelectedItem = -2;
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
                titleCenterPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin), BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin)));
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
                centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(VV.margin, 0, VV.margin, 0, VV.bgDarkColor), BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin)));
                DarkTextField searchField = new DarkTextField("","Keresés", true);
                IconButton searchButton = new IconButton("", new ImageIcon("assets/white/search.png")); {
                    searchButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0, VV.mainTextColor), BorderFactory.createEmptyBorder(VV.margin/2, VV.margin/2, VV.margin/2, VV.margin/2)));
                    searchButton.setToolTipText("Keresés");
                    searchButton.setOpaque(false);
                }
                centerPanel.add(searchField, BorderLayout.CENTER);
                centerPanel.add(searchButton, BorderLayout.EAST);
            }
            searchPanel.add(centerPanel);
        }
        // HEADER PANEL for the tool buttons
        headerPanel = new JPanel(new BorderLayout()); {
            headerPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
            headerPanel.setBackground(VV.bgDarkColor);
            JPanel centerPanel = new JPanel(); {
                centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
                centerPanel.setBackground(VV.bgLightColor);
                centerPanel.setAlignmentY(Component.CENTER_ALIGNMENT);
                centerPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
                centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(VV.margin, 0, VV.margin, VV.margin, VV.bgDarkColor), BorderFactory.createEmptyBorder(0, VV.margin,0,VV.margin)));
//                IconButton saveButton = new IconButton("Mentés", new ImageIcon("assets/white/save.png")); {
//                    saveButton.setToolTipText("Mentés");
//                    saveButton.setOpaque(false);
//                }
//                centerPanel.add(saveButton);
                IconButton logOutButton = new IconButton("Kijelentkezés", new ImageIcon("assets/white/logout.png")); {
                    logOutButton.setToolTipText("Kijelentkezés");
                    logOutButton.setOpaque(false);
                    logOutButton.addActionListener(_ -> {
                        try {
                            API.logoutRequest();
                        } catch (Exception ex) {
                            System.out.println(ex.toString());
                        }
                        this.dispose();
                    });
                }
                centerPanel.add(logOutButton);
            }
            headerPanel.add(centerPanel);
        }
        // SIDE PANEL for filtering for categories
        sidePanel = new JPanel(new BorderLayout()); {
            sidePanel.setBackground(VV.bgLightColor);
            JPanel categoryLabelPanel = new JPanel(new BorderLayout()); {
                categoryLabelPanel.setBorder(BorderFactory.createMatteBorder(0, VV.margin,0, VV.margin, VV.bgDarkColor));
                categoryLabelPanel.setOpaque(false);
                JLabel categoryLabel = new JLabel("Kategóriák"); {
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    categoryLabel.setForeground(VV.mainTextColor);
                    categoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    categoryLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin), BorderFactory.createMatteBorder(0,0,2,0, Color.white)));
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
                scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, VV.margin, 0, VV. margin, VV.bgDarkColor), BorderFactory.createEmptyBorder(0, VV.margin, VV.margin, VV.margin)));
                scrollPane.setOpaque(false);
                scrollPane.setBackground(VV.bgLightColor);
                scrollPane.getViewport().setOpaque(false);
            }
            sidePanel.add(scrollPane, BorderLayout.CENTER);

            JPanel categoryEditorToolPanel = new JPanel(); {
                categoryEditorToolPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                categoryEditorToolPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
                categoryEditorToolPanel.setBorder(BorderFactory.createMatteBorder(0, VV.margin, VV.margin, VV.margin, VV.bgDarkColor));
                categoryEditorToolPanel.setOpaque(false);
                IconButton addCategoryButton = new IconButton("", new ImageIcon("assets/white/plus.png")); {
                    addCategoryButton.setToolTipText("Új kategória");
                    addCategoryButton.addActionListener(event -> {
                        try {
                            String categoryName = JOptionPane.showInputDialog(this,"Írja be az új kategória nevét!", "Új kategória", JOptionPane.QUESTION_MESSAGE);
                            if (categoryName != null) {
                                JSONObject json = new JSONObject();
                                json.put("category", categoryName);
                                if (API.addNewCategory(json))
                                    System.out.println("Sikeres kategória hozzáadás");
                                else
                                    System.out.println("Nem sikerült a kategória hozzáadás");
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                        refresh();
                    });
                }
                IconButton editCategoryButton = new IconButton("", new ImageIcon("assets/white/setting.png")); {
                    editCategoryButton.setToolTipText("Kategoria szerkesztése");
                    editCategoryButton.addActionListener(event -> {
                        try {
                            TreePath tp = categoryTree.getSelectionPath();
                            if (tp != null) {
                                String oldCategory = tp.getLastPathComponent().toString();
                                String categoryName = (String) JOptionPane.showInputDialog(this, "Szerkessze a kategória nevét!", "Kategória szerkesztése", JOptionPane.QUESTION_MESSAGE, null, null, tp.getLastPathComponent().toString());
                                JSONObject json = new JSONObject();
                                json.put("category", categoryName);
                                json.put("oldCategory", oldCategory);
                                if (API.modifyCategory(json))
                                    System.out.println("Sikeresk kategória szerkesztés");
                                else
                                    System.out.println("Nem sikerült a kategóriát szerkeszteni");
                                refresh();
                            }
                        } catch (Exception e){
                            System.out.println(e);
                        }
                    });
                }
                IconButton removeCategoryButton = new IconButton("", new ImageIcon("assets/white/trash.png")); {
                    removeCategoryButton.setToolTipText("Kategória törlése");
                    removeCategoryButton.addActionListener(event -> {
                        try {
                            TreePath tp = categoryTree.getSelectionPath();
                            if (tp != null) {
                                String oldCategory = tp.getLastPathComponent().toString();
                                JSONObject json = new JSONObject();
                                json.put("category", oldCategory);
                                if (API.removeCategory(json))
                                    System.out.println("Sikeres törlés");
                                else
                                    System.out.println("Nem sikerült a törlés");
                                refresh();
                            }
                        } catch (Exception e){
                            System.out.println(e);
                        }
                    });
                }
                categoryEditorToolPanel.add(addCategoryButton, BorderLayout.WEST);
                categoryEditorToolPanel.add(editCategoryButton, BorderLayout.CENTER);
                categoryEditorToolPanel.add(removeCategoryButton, BorderLayout.EAST);
            }
            sidePanel.add(categoryEditorToolPanel, BorderLayout.SOUTH);
        }
        // SLIDER PANEL for displaying every
        sliderPanel = new JPanel(new BorderLayout()); {
            sliderPanel.setBackground(VV.bgDarkColor);
            itemJList = createItemJList(API.getItemList(null)); {
                itemJList.setCellRenderer(new ItemRenderer());
                itemJList.setBackground(VV.bgLightColor);
                itemJList.addListSelectionListener(event -> {
                    try {
                        int idx = itemJList.getSelectedIndex();
                        if (nextSelectedItem == idx && nextSelectedItem == idx) {
                            itemJList.removeSelectionInterval(0,idx);
                        } else {
                            displayItem(selectedItemIndex);
                            nextSelectedItem = selectedItemIndex;
                            selectedItemIndex = idx;
                        }
//                        Item selectedItem = (Item) itemJList.getSelectedValue();
//                        JSONObject json = selectedItem.toJSON();
                        System.out.println(selectedItemIndex);

                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });

            }
            JScrollPane itemListScrollPane = new JScrollPane(itemJList); {
                itemListScrollPane.setBackground(VV.bgDarkColor);
                itemListScrollPane.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, VV.bgDarkColor));
                itemListScrollPane.getViewport().setOpaque(false);
            }
            sliderPanel.add(itemListScrollPane, BorderLayout.CENTER);
            IconButton addNewItemButton = new IconButton("Új bejegyzés", new ImageIcon("assets/white/plus.png")); {
                addNewItemButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, VV.margin, 0, VV.bgDarkColor), BorderFactory.createEmptyBorder(VV.margin,0,VV.margin,0)));
                addNewItemButton.setBackground(VV.bgLightColor);
                addNewItemButton.setToolTipText("Új bejegyzés");
//                addNewItemButton.setOpaque(false);
            }
            sliderPanel.add(addNewItemButton, BorderLayout.SOUTH);
        }
        // EDITOR PANEL for editing items
        contentBackPanel = new JPanel(new BorderLayout()); {
            contentBackPanel.setBackground(VV.bgDarkColor);
//            contentBackPanel.setLayout(new BoxLayout(contentBackPanel, BoxLayout.Y_AXIS));
            editorPanel = new JPanel(); {
                editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
                editorPanel.setBorder(BorderFactory.createMatteBorder(0, VV.margin, VV.margin, VV.margin, VV.bgDarkColor));
                editorPanel.setBackground(VV.bgLightColor);
//               editorPanel.setOpaque(false);
//                JPanel titleRow = new JPanel(new BorderLayout()); {
//                    titleRow.setOpaque(false);
//                    titleRow.setBorder(BorderFactory.createEmptyBorder(VV.margin, VV.margin, VV.margin, VV.margin));
//                    IconButton iconButton = new IconButton("", new ImageIcon("assets/white/picture.png"));
//                    titleRow.add(iconButton, BorderLayout.WEST);
//                    DarkTextField titleField = new DarkTextField("", "Bejegyzés címe");
//                    titleField.setUnderline(true);
//                    titleRow.add(titleField, BorderLayout.CENTER);
//                }
//                editorPanel.add(titleRow);
                // PLACEHOLDER FIELDS
                FieldPanel titleFieldPanel = new FieldPanel("Cím");
                editorPanel.add(titleFieldPanel);
                FieldPanel usernameFieldPanel = new FieldPanel("Felhasználónév");
                editorPanel.add(usernameFieldPanel);
                FieldPanel passwordFieldPanel = new FieldPanel("Jelszó");
                editorPanel.add(passwordFieldPanel);
                FieldPanel descriptionFieldPanel = new FieldPanel("Megjegyzés");
                editorPanel.add(descriptionFieldPanel);
                editorPanel.setVisible(false);
            }
            contentBackPanel.add(editorPanel, BorderLayout.CENTER);
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

    private JList<Item> createItemJList(ArrayList<Item> itemList) {
        // Create list model
        DefaultListModel<Item> model = new DefaultListModel<>();
        // Add items to model
        if (itemList != null) {
            for (Item i : itemList)
                model.addElement(i);
        }
        return new JList<Item>(model);
    }

    private void refresh(){
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

    private void displayItem(int itemIndex) {
        editorPanel.setVisible(true);
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
