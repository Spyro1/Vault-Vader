package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * Represents a central hub for the application, handling user management, data persistence, categories, and items.
 */
public class Controller {
    
    /**
     * Singleton instance of the class
     */
    public static final Controller INSTANCE = new Controller();
    
    /**
     * Stores the current user's password items
     */
    private final ArrayList<Item> items = new ArrayList<>();
    
    /**
     * Stores the current user's categories
     */
    private final HashSet<String> categories = new HashSet<>(); 
    
    /**
     * The name of the user currently logged in to the app
     */
    private User loggedInUser;
    
    /**
     * Temporal item for displaying the item data on screen and then saving the changes
     */
    private Item tempItem = null;
    
    private final String usersFolderPath = "users/";
    
    /**
     * Not accessible constructor
     */
    private Controller() {
    }
    
    // === Read / Write user data functions ===
    
    /**
     * Reads user data from a file for the currently logged-in user.
     *
     * <p>This method performs the following operations:
     * <ul>
     *   <li>Checks whether the user data folder exists. If it does not, attempts to create it.</li>
     *   <li>Clears the `categories` and `items` lists before reading new data.</li>
     *   <li>Attempts to read the user data file located at the path corresponding to the logged-in user.</li>
     *   <li>If the file exists and contains user data, it populates the `categories` and `items` lists using the file's content.</li>
     *   <li>If any exception occurs during file reading or parsing, logs the error message to the console.</li>
     *   <li>If the user file does not exist, logs an error indicating the missing file.</li>
     * </ul>
     *
     * <p>Debug and error messages are printed to the console throughout the process.
     */
    private void readUsersDataFromFile() {
        // Check for users folder
        File folder = new File(usersFolderPath);
        if (!folder.exists()) {
            // If the folder does not exist, create it
            if (folder.mkdirs()) {
                System.out.println("DEBUG/Controller/readUsersData: \"" + usersFolderPath + "\" folder created successfully.");
            } else {
                System.out.println("DEBUG/Controller/readUsersData: Failed to create \"" + usersFolderPath + "\" the folder.");
            }
        } else {
            System.out.println("DEBUG/Controller/readUsersData: \"" + usersFolderPath + "\" folder already exists.");
        }
        // Initialize arrays
        categories.clear();
        items.clear();
        // try to read user data
        File userFile = new File(getLoggedInUserFilePath());
        if (userFile.exists()) {
            try {
                JSONObject usersData = (JSONObject) new JSONParser().parse(new FileReader(getLoggedInUserFilePath())); // Character files
//                JSONObject usersData = (JSONObject) new JSONParser().parse(new InputStreamReader(new GZIPInputStream(new FileInputStream(getLoggedInUserFilePath())))); // zipped binary files
                if (usersData.containsKey(API.CATEGORY_KEY)) {
                    JSONArray categoryArray = (JSONArray) usersData.get(API.CATEGORY_KEY);
                    for (Object categoryObj : categoryArray) {
                        categories.add(categoryObj.toString());
                    }
                }
                if (usersData.containsKey(API.ITEMS_KEY)) {
                    JSONArray itemArray = (JSONArray) usersData.get(API.ITEMS_KEY);
                    for (Object itemObj : itemArray) {
                        Item item = new Item().fromJSON((JSONObject) itemObj);
                        items.add(item);
                    }
                }
            } catch (Exception e) {
                System.err.println("ERROR/Controller/readUsersDataFromFile: " + e);
            }
        } else {
            System.err.println("ERROR/Controller/readUsersDataFromFile: \"" + getLoggedInUserFilePath() + "\" file does not exist");
        }
    }
    
    
    /**
     * Writes the current user's data (username, password, categories, and items) to a file in JSON format.
     *
     * <p>This method performs the following operations:
     * <ul>
     *   <li>Constructs a JSON object containing the logged-in user's username, password, categories, and items.</li>
     *   <li>Converts the `items` list to JSON format and adds it to the JSON object.</li>
     *   <li>Attempts to write the JSON object to a file located at the path corresponding to the logged-in user.</li>
     *   <li>If any exception occurs during file writing, logs the error message to the console.</li>
     * </ul>
     *
     * <p>Error messages are printed to the console if any issues arise during the writing process.
     */
    private void writeUserDateToFile() {
        JSONObject json = new JSONObject();
        // Build json
        json.put(API.USERNAME_KEY, loggedInUser.getName());
        json.put(API.PASSWORD_KEY, loggedInUser.getPassword());
        // Create category array
        JSONArray categoryArray = new JSONArray();
        categoryArray.addAll(categories); // Add all categories to JSONArray
        json.put(API.CATEGORY_KEY, categoryArray);
        // Add all items projected to JSON format in an array to the JSON object
        json.put(API.ITEMS_KEY, items.stream().map(Item::toJSON).collect(Collectors.toList()));
        // Write out to file
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(getLoggedInUserFilePath())); // Character files
//            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(getLoggedInUserFilePath())))); // Zipped binary files
            // Print to stream
            pw.write(json.toJSONString()); //.replace(",", ",\n").replace("{", "{\n").replace("}", "\n}").replace("[", "[\n").replace("]", "\n]")); // Formatting
            pw.flush();
            pw.close();
        } catch (Exception e) {
            System.err.println("ERROR/Controller/writeUserDateToFile: " + e.getMessage());
        }
    }
    
    // === API called public functions bellow ===
    
    // == Encryption / Decryption functions ==
    
    /**
     * Encrypts the given text using the provided key with a simple XOR cipher.
     * @param text the plain text to be encrypted
     * @param key the key used for the encryption
     * @return the encrypted string
     */
    public static String encryptText(String text, String key) {
        StringBuilder result = new StringBuilder(); // init string builder object
        // Encrypt every character one by one
        for (int i = 0; i < text.length(); i++) {
            char res = (char) (text.charAt(i) ^ key.charAt(i % key.length())); // Make an XOR bitwise from the password and the key
            result.append(res); // Concatenate to the string
        }
        return result.toString();
    }
    
    /**
     * Decrypts the given text using the provided key.
     * @param text the encrypted text to be decrypted
     * @param key the key used for decryption (same key used for encryption)
     * @return the decrypted string (original plain text)
     */
    public static String decryptText(String text, String key) {
        return encryptText(text, key); // Symmetric encryption - decryption
    }
    
    // == User Data Management ==
    
    /**
     * Retrieves the currently logged-in user.
     *
     * <p>This method returns the {@link User} object that represents the user
     * currently logged into the application. The object contains details such as
     * the user's username and password (encrypted).
     *
     * @return the {@link User} object representing the logged-in user, or
     * {@code null} if no user is currently logged in.
     */
    public User getLoggedInUser() {
        return loggedInUser;
    }
    
    /**
     * Gets the file path for a given username.
     * @param username the username of the user
     * @return the file path for the user's data
     */
    private String getUserFilePath(String username) {
        return usersFolderPath + username + ".json";
    }
    
    /**
     * Gets the file path for the currently logged-in user's data.
     * @return the file path for the logged-in user's data
     */
    private String getLoggedInUserFilePath() {
        return getUserFilePath(loggedInUser.getName());
    }
    
    
    /**
     * Loads the data of the currently logged-in user, if any.
     */
    public void loadUser() {
        if (loggedInUser != null) {
            readUsersDataFromFile(); // Reads logged in user's data from file
        }
    }
    
    /**
     * Checks if a user exists and verifies the password from the provided JSON data.
     * @param userData the JSON object containing username and encrypted password
     * @return true if the user exists and the password is correct, false otherwise
     * @throws Exception if the user does not exist
     */
    public boolean checkUser(JSONObject userData) throws Exception {
        String username = userData.get(API.USERNAME_KEY).toString();
        String password = userData.get(API.PASSWORD_KEY).toString(); // Encrypted password
        
        File userFile = new File(getUserFilePath(username)); // Create a temporal file for user
        // Check if user exists
        if (userFile.exists()) {
            // Read data from the user file
//            BufferedReader userReader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(getUserFilePath(username)))));
            BufferedReader userReader = new BufferedReader(new FileReader(getUserFilePath(username)));
            JSONObject userDataFromFile = (JSONObject) new JSONParser().parse(userReader);
            userReader.close();
            // Check if the encrypted password is correct
            String encryptedPassword = userDataFromFile.get(API.PASSWORD_KEY).toString();
            if (encryptedPassword.equals(password)) {
                loggedInUser = new User(username, encryptedPassword);
                return true; // True, if the user exits and the password is correct
            } else {
                loggedInUser = null;
                return false; // False, if the user exists, but the password is incorrect
            }
        } else {
            throw new Exception("Nem található ilyen nevű felhasználó!\nKérem regisztráljon, vagy lépjen be más felhasználóval."); // Throw exception if the user does not exists
        }
    }
    
    /**
     * Creates a new user if the username does not already exist, initializes default categories, and writes data to
     * file.
     * @param userData the JSON object containing username and encrypted password
     * @return true if the user is successfully created, false if the user already exists
     */
    public boolean createUser(JSONObject userData) {
        String username = userData.get(API.USERNAME_KEY).toString();
        String password = userData.get(API.PASSWORD_KEY).toString(); // Encrypted password
        
        File userFile = new File(getUserFilePath(username)); // Create a temporal file for user
        // Check if user exists
        if (!userFile.exists()) {
            // Create new user
            loggedInUser = new User(username, password);
            // Setup default values //
            categories.clear();
            items.clear();
            // Create default categories
            categories.add("Email");
            categories.add("Pénzügyek");
            categories.add("Egyéb");
            // Create a file for the new user
            writeUserDateToFile();
            return true;
        } else {
            return false; // If the username.json file exists, then return false, meaning the username already exists
        }
    }
    
    /**
     * Saves all data of the logged-in user to a file.
     */
    public void saveAll() {
        writeUserDateToFile();
    }
    
    
    // == Category Management ==
    
    /**
     * Retrieves the list of user-defined categories.
     * @return a HashSet containing all categories
     */
    public Set<String> getCategoryList() {
        return categories;
    }
    
    /**
     * Adds a new category to the category list if it doesn't already exist.
     * @param newCategory the name of the new category
     * @return true if the category is successfully added, false if it already exists
     */
    public boolean addNewCategory(String newCategory) {
        if (!categories.contains(newCategory)) {
            categories.add(newCategory);
            return true;
        }
        return false;
    }
    
    /**
     * Modifies an existing category by replacing it with a new category.
     * @param oldCategory the current category name
     * @param newCategory the new category name to replace the old one
     * @return true if the category is successfully replaced, false if the old category was not found
     */
    public boolean modifyCategory(String oldCategory, String newCategory) {
        if (categories.remove(oldCategory)) {
            return categories.add(newCategory); // Successful category replacement
        } else {
            return false; // Old category not found (This is not a likely branch, but can happen)
        }
    }
    
    /**
     * Removes a category from the category list.
     * @param categoryToRemove the name of the category to remove
     * @return true if the category is successfully removed, false if the category was not found
     */
    public boolean removeCategory(String categoryToRemove) {
        return categories.remove(categoryToRemove);
    }
    
    
    // == Item Management ==
    
    /**
     * Retrieves an item by its index in the item list.
     * @param index the index of the item in the list
     * @return the item at the specified index
     */
    public Item getItem(int index) {
        return items.get(index);
    }
    
    /**
     * Retrieves the list of all items.
     * @return an ArrayList containing all items
     */
    public ArrayList<Item> getItemList() {
        return items;
    }
    
    // == Temporal Item Management ==
    
    /**
     * Creates a new temporary item and returns it.
     * @return the newly created temporary item
     */
    public Item newTemporalItem() {
        tempItem = new Item();
        return tempItem;
    }
    
    /**
     * Retrieves the current temporary item.
     * @return the current temporary item
     */
    public Item getTemporalItem() {
        return tempItem;
    }
    
    /**
     * Sets a reference to the current temporary item.
     * @param itemReference the item to be set as the temporary item
     * @return the updated temporary item
     */
    public Item setTemporalItem(Item itemReference) {
        return tempItem = itemReference;
    }
    
    /**
     * Saves the current temporary item.
     *
     * <p>If the item has blank fields (title or category), it is not saved.
     * If the item already exists in the item list, it is modified. Otherwise, it is added to the list.
     * @return true if the item is successfully saved or added, false if it has blank fields
     */
    public boolean saveTemporalItem() {
        if (tempItem.getTitle() != null && tempItem.getCategory() != null && tempItem.getTitle().isBlank() || tempItem.getCategory().getValue().isBlank()) {
            return false; // Blank fields -> not saved
        } else {
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).getID() == tempItem.getID()) {
                    items.set(i, tempItem);
                    return true; // Old item modified
                }
            }
            return items.add(tempItem); // New item added -> return if it is successfully added to the list
        }
    }
}
