package frontend.ui;

import backend.API;
import backend.item.Item;
import frontend.VV;
import frontend.customComponents.*;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.json.simple.JSONObject;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;

public class MainUI extends JFrame {

    final int width = 1000, height = 600; // Default  size of the window
    final double headerWeightY = 0.01, contentWeightY = 1- headerWeightY,
                 firsColWeight = 0.1, secondColWeight = 0.4, thirdColWeight = 0.5;
    JPanel titlePanel, searchPanel, headerPanel, sidePanel, sliderPanel, contentBackPanel;
    ItemEditorPanel editorPanel; //, categoryPanel;
    JScrollPane editorContentScroller;
    JTree categoryTree;
    JList<Item> itemJList;
    JSONObject displayedItem = null;
    int singlifyer = 0;
    public int selectedItemIndex = -1;
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
                categoryTree.setCellRenderer(new CategoryTreeRenderer());
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

            JPanel categoryEditorToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                categoryEditorToolPanel.setBorder(BorderFactory.createMatteBorder(0, VV.margin, VV.margin, VV.margin, VV.bgDarkColor));
                categoryEditorToolPanel.setOpaque(false);
                IconButton addCategoryButton = new IconButton("", new ImageIcon("assets/white/plus.png")); {
                    addCategoryButton.setToolTipText("Új kategória");
                    addCategoryButton.addActionListener(event -> {
                        try {
                            String categoryName = JOptionPane.showInputDialog(this,"Írja be az új kategória nevét!", "Új kategória", JOptionPane.QUESTION_MESSAGE);
                            if (categoryName != null) {
                                JSONObject json = new JSONObject();
                                json.put(API.CATEGORY_KEY, categoryName);
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
                                json.put(API.NEW_CATEGORY_KEY, categoryName);
                                json.put(API.OLD_CATEGORY_KEY, oldCategory);
                                if (categoryName != null && oldCategory != null && API.modifyCategory(json)) {
                                    System.out.println("Sikeresk kategória szerkesztés"); // JUST FOR DEBUG
                                    refresh();
                                } else {
                                    System.out.println("Nem sikerült a kategóriát szerkeszteni"); // JUST FOR DEBUG
                                }
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
                                json.put(API.CATEGORY_KEY, oldCategory);
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
            sliderPanel.setBackground(VV.bgLightColor);
            sliderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, VV.margin, 0, VV.bgDarkColor));
            itemJList = createItemJList(API.getItemList(null)); {
                itemJList.setCellRenderer(new ItemCellRenderer());
                itemJList.setBackground(VV.bgLightColor);
                itemJList.addListSelectionListener(event -> {
                    try {
                        // TODO: Selection and unselection to fix!
                        selectedItemIndex = itemJList.getSelectedIndex();
//                        if (singlifyer == 0 && selectedItemIndex == idx) {
//                            itemJList.removeSelectionInterval(0,idx);
//                            editorPanel.setVisible(false);
//                        } else if (singlifyer == 0) {
                            displayItem(selectedItemIndex);
//                            selectedItemIndex = idx;
                            System.out.println(selectedItemIndex);
//                        }
//                        singlifyer = ++singlifyer % 2;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                });

            }
            JScrollPane itemListScrollPane = new JScrollPane(itemJList); {
                itemListScrollPane.setBackground(VV.bgLightColor);
                itemListScrollPane.setBorder(BorderFactory.createEmptyBorder());
                itemListScrollPane.getViewport().setOpaque(false);
            }
            sliderPanel.add(itemListScrollPane, BorderLayout.CENTER);
            JPanel itemToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                itemToolPanel.setOpaque(false);
//                itemToolPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, VV.bgDarkColor));
                IconButton addNewItemButton = new IconButton("Új bejegyzés", new ImageIcon("assets/white/plus.png")); {
//                    addNewItemButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, VV.margin, 0, VV.bgDarkColor), BorderFactory.createEmptyBorder(VV.margin,0,VV.margin,0)));
//                    addNewItemButton.setBackground(VV.bgLightColor);
                    addNewItemButton.setToolTipText("Új bejegyzés");
                    addNewItemButton.addActionListener(event -> {
                        API.addNewItem(null); // TODO: Write add new item action
                    });
                }
                itemToolPanel.add(addNewItemButton);
            }
            sliderPanel.add(itemToolPanel, BorderLayout.SOUTH);
        }
        // EDITOR PANEL for editing items
        contentBackPanel = new JPanel(new BorderLayout()); {
            contentBackPanel.setBackground(VV.bgLightColor);
            contentBackPanel.setBorder(BorderFactory.createMatteBorder(0, VV.margin, VV.margin, VV.margin, VV.bgDarkColor));
//            contentBackPanel.setLayout(new BoxLayout(contentBackPanel, BoxLayout.Y_AXIS));
            editorPanel = new ItemEditorPanel(this); {
//                editorPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//                editorPanel.setLayout(new BoxLayout(editorPanel, BoxLayout.Y_AXIS));
//                editorPanel.setBorder(BorderFactory.createMatteBorder(0, VV.margin, VV.margin, VV.margin, VV.bgDarkColor));
//                editorPanel.setBackground(VV.bgLightColor);
//               editorPanel.setOpaque(false);

                editorPanel.setVisible(false);
            }
            /*JScrollPane*/ editorContentScroller = new JScrollPane(editorPanel); {
//                editorContentScroller.setLayout(new BorderLayout());
                editorContentScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                editorContentScroller.setBackground(VV.bgLightColor);
                editorContentScroller.setBorder(BorderFactory.createEmptyBorder());
                editorContentScroller.getViewport().setOpaque(false);
            }
            contentBackPanel.add(editorContentScroller, BorderLayout.CENTER);
            JPanel itemToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
                itemToolPanel.setOpaque(false);
                IconButton saveItemChanges = new IconButton("Mentés", new ImageIcon("assets/white/save.png")); {
                    saveItemChanges.setToolTipText("Mentés");
                    saveItemChanges.addActionListener(event -> {
                        API.saveItem(displayedItem); // TODO: Write save item action
                    });
                }
                itemToolPanel.add(saveItemChanges);
            }
            contentBackPanel.add(itemToolPanel, BorderLayout.SOUTH);
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

    private JList<Item> createItemJList(Collection<Item> itemList) {
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
        Collection<String> categories = API.getCategoryList(); // GET data from API
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
        displayedItem = API.getItemData(itemIndex);
        editorPanel.displayItem(displayedItem);
//        editorPanel.validate();
        editorContentScroller.validate();
        editorPanel.setVisible(true);
    }
}
