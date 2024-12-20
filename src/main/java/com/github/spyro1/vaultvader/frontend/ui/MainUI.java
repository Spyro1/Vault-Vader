package com.github.spyro1.vaultvader.frontend.ui;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.backend.Item;
import com.github.spyro1.vaultvader.frontend.UI;
import com.github.spyro1.vaultvader.frontend.customcomponents.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.json.simple.JSONObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Collection;
import java.util.Set;

public class MainUI extends JFrame {
    
    // Default size of the window
    static final int WIDTH = 1000;
    static final int HEIGHT = 600; 
    // GridBagView constant modifiers
    static final double HEADER_WEIGHT_Y = 0.01;
    static final double CONTENT_WEIGHT_Y = 1 - HEADER_WEIGHT_Y;
    static final double FIRST_COL_WEIGHT = 0.1;
    static final double SECOND_COL_WIGHT = 0.4;
    static final double THIRD_COL_WEIGHT = 0.5;
    // Objects
    JPanel titlePanel, searchPanel, headerPanel, sidePanel, sliderPanel, contentBackPanel;
    ItemEditorPanel editorPanel;
    JScrollPane editorContentScroller, itemListScrollPane;
    JTree categoryTree;
    JList<Item> itemJList;
    // Functioning variables
    int selectedItemIndex = -1;
    // JSONObject filter = new JSONObject();
    DefaultMutableTreeNode allItemCategory = new DefaultMutableTreeNode("Minden bejegyzés");
    int singlifyer = 0;
    
    public MainUI() {
        // Create main frame
        setTitle("Vault Vader");
        setSize(WIDTH, HEIGHT);
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
                try {
                    API.saveAllChanges(); // Call API to save changes to the user data
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
                e.getWindow().dispose(); // Close the window
            }
        });
        
        // TITLE PANEL for displaying the app name
        titlePanel = new JPanel(new BorderLayout());
        {
            JPanel titleCenterPanel = new JPanel(new BorderLayout());
            {
                titleCenterPanel.setBackground(UI.bgDarkColor);
                titleCenterPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin), BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin)));
                JLabel VaultVaderLabel = new JLabel("Vault Vader", JLabel.CENTER);
                {
                    VaultVaderLabel.setForeground(UI.mainTextColor);
                    VaultVaderLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
                    VaultVaderLabel.setFont(new Font("Impact", Font.BOLD, 30));
                    titleCenterPanel.add(VaultVaderLabel);
                }
            }
            titlePanel.add(titleCenterPanel);
        }
        // SEARCH PANEL for the search bar
        searchPanel = new JPanel(new BorderLayout());
        {
            searchPanel.setBackground(UI.bgLightColor);
            JPanel centerPanel = new JPanel(new BorderLayout());
            {
                centerPanel.setBackground(UI.bgLightColor);
                centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(UI.margin, 0, UI.margin, 0, UI.bgDarkColor), BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin)));
                // === REMOVED FOR BETA RELEASE ===
                // TODO: Search field implementation
//                DarkTextField searchField = new DarkTextField("","Keresés", true);
//                IconButton searchButton = new IconButton("", "search.png"); {
//                    searchButton.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0,0,2,0, UI.mainTextColor), BorderFactory.createEmptyBorder(UI.margin/2, UI.margin/2, UI.margin/2, UI.margin/2)));
//                    searchButton.setToolTipText("Keresés");
//                    searchButton.addActionListener(this::searchButtonClicked);
//                }
//                centerPanel.add(searchField, BorderLayout.CENTER);
//                centerPanel.add(searchButton, BorderLayout.EAST);
            }
            searchPanel.add(centerPanel);
        }
        // HEADER PANEL for the tool buttons
        headerPanel = new JPanel(new BorderLayout());
        {
            headerPanel.setBackground(UI.bgDarkColor);
            JPanel centerPanel = new JPanel();
            {
                centerPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                centerPanel.setBackground(UI.bgLightColor);
                centerPanel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(UI.margin, 0, UI.margin, UI.margin, UI.bgDarkColor), BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin)));
                IconButton logOutButton = new IconButton("Kijelentkezés", "logout.png");
                {
                    logOutButton.setToolTipText("Kijelentkezés");
                    logOutButton.setBorder(BorderFactory.createEmptyBorder(UI.margin / 2, UI.margin / 2, UI.margin / 2, UI.margin / 2));
                    logOutButton.addActionListener(e -> {
                        try {
                            API.logoutRequest(); // Send a request to the API to log out the user
                        } catch (Exception ex) {
                            System.out.println(ex.getMessage());
                        }
                        this.dispose();
                    });
                }
                centerPanel.add(logOutButton);
            }
            headerPanel.add(centerPanel);
        }
        // SIDE PANEL for filtering for categories
        sidePanel = new JPanel(new BorderLayout());
        {
            sidePanel.setBackground(UI.bgLightColor);
            JPanel categoryLabelPanel = new JPanel(new BorderLayout());
            {
                categoryLabelPanel.setBorder(BorderFactory.createMatteBorder(0, UI.margin, 0, UI.margin, UI.bgDarkColor));
                categoryLabelPanel.setOpaque(false);
                JLabel categoryLabel = new JLabel("Kategóriák");
                {
                    categoryLabel.setForeground(UI.mainTextColor);
                    categoryLabel.setForeground(UI.mainTextColor);
                    categoryLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    categoryLabel.setForeground(UI.mainTextColor);
                    categoryLabel.setFont(new Font("Arial", Font.PLAIN, 20));
                    categoryLabel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(UI.margin, UI.margin, UI.margin, UI.margin), BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white)));
                }
                categoryLabelPanel.add(categoryLabel);
            }
            sidePanel.add(categoryLabelPanel, BorderLayout.NORTH);
            
            categoryTree = new JTree(allItemCategory);
            {
                categoryTree.setOpaque(false);
                categoryTree.setFont(new Font("Arial", Font.PLAIN, 15));
                categoryTree.setCellRenderer(new CategoryTreeRenderer());
                categoryTree.addTreeSelectionListener(this::categorySelected);
            }
            refreshCategoryTree(); // Refresh added category tree
            JScrollPane scrollPane = new JScrollPane(categoryTree);
            {
                scrollPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, UI.margin, 0, UI.margin, UI.bgDarkColor), BorderFactory.createEmptyBorder(0, UI.margin, UI.margin, UI.margin)));
                scrollPane.setOpaque(false);
                scrollPane.setBackground(UI.bgLightColor);
                scrollPane.getViewport().setOpaque(false);
            }
            sidePanel.add(scrollPane, BorderLayout.CENTER);
            
            JPanel categoryEditorToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            {
                categoryEditorToolPanel.setBorder(BorderFactory.createMatteBorder(0, UI.margin, UI.margin, UI.margin, UI.bgDarkColor));
                categoryEditorToolPanel.setOpaque(false);
                IconButton addCategoryButton = new IconButton("", "plus.png");
                {
                    addCategoryButton.setToolTipText("Új kategória");
                    addCategoryButton.addActionListener(this::addCategoryClicked);
                }
                IconButton editCategoryButton = new IconButton("", "setting.png");
                {
                    editCategoryButton.setToolTipText("Kategoria szerkesztése");
                    editCategoryButton.addActionListener(this::editCategoryClicked);
                }
                IconButton removeCategoryButton = new IconButton("", "trash.png");
                {
                    removeCategoryButton.setToolTipText("Kategória törlése");
                    removeCategoryButton.addActionListener(this::removeCategoryClicked);
                }
                categoryEditorToolPanel.add(addCategoryButton, BorderLayout.WEST);
                categoryEditorToolPanel.add(editCategoryButton, BorderLayout.CENTER);
                categoryEditorToolPanel.add(removeCategoryButton, BorderLayout.EAST);
            }
            sidePanel.add(categoryEditorToolPanel, BorderLayout.SOUTH);
        }
        // SLIDER PANEL for displaying every
        sliderPanel = new JPanel(new BorderLayout());
        {
            sliderPanel.setBackground(UI.bgLightColor);
            sliderPanel.setBorder(BorderFactory.createMatteBorder(0, 0, UI.margin, 0, UI.bgDarkColor));
            itemListScrollPane = new JScrollPane();
            {
                itemListScrollPane.setBackground(UI.bgLightColor);
                itemListScrollPane.setBorder(BorderFactory.createEmptyBorder());
                itemListScrollPane.getViewport().setOpaque(false);
            }
            sliderPanel.add(itemListScrollPane, BorderLayout.CENTER);
            JPanel itemToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            {
                itemToolPanel.setOpaque(false);
                IconButton addNewItemButton = new IconButton("Új bejegyzés", "plus.png");
                {
                    addNewItemButton.setToolTipText("Új bejegyzés");
                    addNewItemButton.addActionListener(this::newItemButtonClicked);
                }
                itemToolPanel.add(addNewItemButton);
            }
            sliderPanel.add(itemToolPanel, BorderLayout.SOUTH);
        }
        // REFRESH ITEM LIST
        refreshItemList(); // Create Item JList and refresh
        // EDITOR PANEL for editing items
        contentBackPanel = new JPanel(new BorderLayout());
        {
            contentBackPanel.setBackground(UI.bgLightColor);
            contentBackPanel.setBorder(BorderFactory.createMatteBorder(0, UI.margin, UI.margin, UI.margin, UI.bgDarkColor));
            editorPanel = new ItemEditorPanel(this);
            {
                editorPanel.setVisible(false);
            }
            editorContentScroller = new JScrollPane(editorPanel);
            {
                editorContentScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                editorContentScroller.setBackground(UI.bgLightColor);
                editorContentScroller.setBorder(BorderFactory.createEmptyBorder());
                editorContentScroller.getViewport().setOpaque(false);
            }
            contentBackPanel.add(editorContentScroller, BorderLayout.CENTER);
            JPanel itemToolPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            {
                itemToolPanel.setOpaque(false);
                IconButton saveItemChanges = new IconButton("Mentés", "save.png");
                {
                    saveItemChanges.setToolTipText("Mentés");
                    saveItemChanges.addActionListener(this::saveItemButtonClicked);
                }
                itemToolPanel.add(saveItemChanges);
            }
            contentBackPanel.add(itemToolPanel, BorderLayout.SOUTH);
        }
        
        /* Setup GridBagLayout properties and add panels */
        {
            
            // title panel: Top-right corner
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = FIRST_COL_WEIGHT;
            gbc.weighty = HEADER_WEIGHT_Y;
            add(titlePanel, gbc);
            
            // search panel: second top cell
            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = SECOND_COL_WIGHT;
            add(searchPanel, gbc);
            
            // header panel: third top cell
            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.weightx = THIRD_COL_WEIGHT;
            add(headerPanel, gbc);
            
            // side panel: Side panel below the first two (left side)
            gbc.gridx = 0;
            gbc.gridy = 1;
            gbc.weightx = FIRST_COL_WEIGHT;
            gbc.weighty = CONTENT_WEIGHT_Y;
            add(sidePanel, gbc);
            
            // slider panel: Between panel 3 and panel 5 (middle)
            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.weightx = SECOND_COL_WIGHT;
            add(sliderPanel, gbc);
            
            // content panel: Below the first two panels (right side)
            gbc.gridx = 2;
            gbc.gridy = 1;
            gbc.weightx = THIRD_COL_WEIGHT;
            add(contentBackPanel, gbc);
        }
        
        // Set window visible
        setVisible(true);
    }
    
    // == Category ==
    private void addCategoryClicked(ActionEvent actionEvent) {
        try {
            // Get selected category name
            String categoryName = JOptionPane.showInputDialog(this, "Írja be az új kategória nevét!", "Új kategória", JOptionPane.QUESTION_MESSAGE);
            if (categoryName != null) { // Test if any is selected
                JSONObject json = new JSONObject();
                json.put(API.CATEGORY_KEY, categoryName); // Add data to json object
                if (API.addNewCategory(json)) { // Call API to create the new category
                    System.out.println("DEBUG/addCategoryClicked: Sikeres kategória hozzáadás");
                    refreshCategoryTree();
                    refreshEditorPanel();
                } else {
                    System.out.println("DEBUG/addCategoryClicked: Nem sikerült a kategória hozzáadás");
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR/addCategoryClicked: " + e);
        }
    }
    
    private void editCategoryClicked(ActionEvent actionEvent) {
        try {
            TreePath tp = categoryTree.getSelectionPath();
            if (tp != null) {
                String oldCategory = tp.getLastPathComponent().toString();
                String categoryName = (String) JOptionPane.showInputDialog(this, "Szerkessze a kategória nevét!", "Kategória szerkesztése", JOptionPane.QUESTION_MESSAGE, null, null, tp.getLastPathComponent().toString());
                JSONObject json = new JSONObject();
                json.put(API.NEW_CATEGORY_KEY, categoryName);
                json.put(API.OLD_CATEGORY_KEY, oldCategory);
                if (categoryName != null && oldCategory != null && API.modifyCategory(json)) {
                    System.out.println("DEBUG/feditCategoryClicked: Sikeresk kategória szerkesztés"); // JUST FOR DEBUG
                    refreshCategoryTree();
                    refreshEditorPanel();
                } else {
                    System.out.println("DEBUG/editCategoryClicked: Nem sikerült a kategóriát szerkeszteni"); // JUST FOR DEBUG
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR/editCategoryClicked: " + e);
        }
    }
    
    private void removeCategoryClicked(ActionEvent actionEvent) {
        try {
            TreePath tp = categoryTree.getSelectionPath();
            if (tp != null) {
                String oldCategory = tp.getLastPathComponent().toString();
                JSONObject json = new JSONObject();
                json.put(API.CATEGORY_KEY, oldCategory);
                if (API.removeCategory(json)) {
                    System.out.println("DEBUG/removeCategoryClicked: Sikeres törlés");
                    refreshCategoryTree();
                    refreshEditorPanel();
                } else {
                    System.out.println("DEBUG/removeCategoryClicked: Nem sikerült a törlés");
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR/removeCategoryClicked: " + e.getMessage());
        }
    }
    
    private void categorySelected(TreeSelectionEvent treeSelectionEvent) {
        // TODO: Write category tree selection listener
    }
    
    // == Search ==
//    private void searchButtonClicked(ActionEvent actionEvent) {
    // TODO: Write search button action
//    }
    
    // == Item ==
    private void newItemButtonClicked(ActionEvent actionEvent) {
        editorPanel.displayItem(API.newTemporalItem());
        itemJList.setSelectedIndex(-1);
        refreshCategoryTree();
    }
    
    private void saveItemButtonClicked(ActionEvent actionEvent) {
        try {
            if (API.saveItem(editorPanel.toJSON())) {
                System.out.println("DEBUG/saveItem: " + API.getTemporalItem().toString());
                refreshItemList();
            } else {
                JOptionPane.showMessageDialog(this, "Nincs minden szükséges mező kitöltve!\nKötelező mezők: Cím, Kategória", "Kötelező mezők", JOptionPane.WARNING_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("ERROR/saveItem: " + e.getMessage());
        }
    }
    
    private void itemSelectedFromList(ListSelectionEvent listSelectionEvent) {
        try {
            if (singlifyer == 0) {
                selectedItemIndex = itemJList.getSelectedIndex();
                Item selectedItem = API.getItemData(selectedItemIndex);
                editorPanel.displayItem(API.setTemporalItem(selectedItem));
                editorContentScroller.validate();
                System.out.println("DEBUG/Item displayed: " + selectedItemIndex + " - " + selectedItem.toString());
                singlifyer = 1;
            } else singlifyer = 0;
        } catch (Exception e) {
            System.out.println("ERROR/ItemSelector/ " + e);
        }
    }
    
    // Helper functions
    private JList<Item> createItemJList(Collection<Item> itemList) {
        // Create list model
        DefaultListModel<Item> model = new DefaultListModel<>();
        // Add items to model
        if (itemList != null) {
            for (Item i : itemList)
                model.addElement(i);
        }
        return new JList<>(model);
    }
    
    public void refreshCategoryTree() {
        // Refresh category tree data
        allItemCategory.removeAllChildren();
        Set<String> categories = API.getCategoryList(); // GET data from API
        for (String category : categories) {
            allItemCategory.add(new DefaultMutableTreeNode(category));
        }
        // Expand the root
        categoryTree.expandRow(0);
        // Reload Tree view
        DefaultTreeModel model = (DefaultTreeModel) categoryTree.getModel();
        model.reload();
    }
    
    public void refreshItemList() {
        itemJList = createItemJList(API.getItemList(null));
        {
            itemJList.setCellRenderer(new ItemCellRenderer());
            itemJList.setBackground(UI.bgLightColor);
            itemJList.addListSelectionListener(this::itemSelectedFromList);
        }
        itemListScrollPane.setViewportView(itemJList);
    }
    
    public void refreshEditorPanel() {
        try {
            editorPanel.displayItem(API.getTemporalItem().fromJSON(editorPanel.toJSON()));
        } catch (Exception e) {
            System.err.println("ERROR/refreshEditorPanel: " + e);
        }
    }
}
