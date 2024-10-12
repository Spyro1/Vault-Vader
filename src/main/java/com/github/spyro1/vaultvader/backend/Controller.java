package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Controller {

    /** Singleton instance of the class */
    public static final Controller INSTANCE = new Controller();

    /** Stores the current user's password items */
    private final ArrayList<Item> items = new ArrayList<>();

    /** Stores the current user's categories */
    private final HashSet<String> categories = new HashSet<>(); // new HashSet<>();

    /** The name of the user currently logged in to the app */
    private User loggedInUser;

    /** Temporal item for displaying the item data on screen and then saving the changes*/
    private Item tempItem = null;// = new Item();

    private final String usersFolderPath = "users/";

    /** Not accessible constructor */
    private Controller() {}

    // === Read / Write user data functions ===
    private void readUsersDataFromFile() {
        // Check for users folder
        File folder = new File(usersFolderPath);
        if (!folder.exists()) {
            // If the folder does not exist, create it
            if (folder.mkdirs()) {
                System.out.println("DEBUG/Controller/readUsersData: \""+ usersFolderPath +"\" folder created successfully.");
            } else {
                System.out.println("DEBUG/Controller/readUsersData: Failed to create \""+ usersFolderPath +"\" the folder.");
            }
        } else {
            System.out.println("DEBUG/Controller/readUsersData: \""+ usersFolderPath +"\" folder already exists.");
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
            System.err.println("ERROR/Controller/readUsersDataFromFile: \""+ getLoggedInUserFilePath()+ "\" file does not exist");
        }
    }
    private void writeUserDateToFile() {
        JSONObject json = new JSONObject();
        // Build json
        json.put(API.USERNAME_KEY, loggedInUser.getName());
        json.put(API.PASSWORD_KEY, loggedInUser.getPassword());
        JSONArray categoryArray = new JSONArray();
        categoryArray.addAll(categories);
        json.put(API.CATEGORY_KEY, categoryArray);
        json.put(API.ITEMS_KEY, items.stream().map(Item::toJSON).collect(Collectors.toList()));
        // Write out to file
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(getLoggedInUserFilePath())); // Character files
//            PrintWriter pw = new PrintWriter(new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(getLoggedInUserFilePath())))); // Zipped binary files
            // Print to stream
            pw.write(json.toJSONString()); //.replace(",", ",\n").replace("{", "{\n").replace("}", "\n}").replace("[", "[\n").replace("]", "\n]")); // Formatting
            pw.flush();
        } catch (Exception e) {
            System.err.println("ERROR/Controller/writeUserDateToFile: " + e.getMessage());
        }
    }

    // == Encryption function ==
    public static String encryptText(String text, String key) {
        StringBuilder result = new StringBuilder(); // init string builder object
        // Encrypt every character one by one
        for (int i = 0; i < text.length(); i++){
            char res = (char) (text.charAt(i) ^ key.charAt(i % key.length())); // Make an XOR bitwise from the password and the key
            result.append(res); // Concatenate to the string
        }
        return result.toString();
    }

    // === API called public functions ===

    // == User ==
    private String getUserFilePath(String username){
        return usersFolderPath + username + ".json";
    }
    private String getLoggedInUserFilePath(){
        return getUserFilePath(loggedInUser.getName());
    }
    public void loadUser() {
        if (loggedInUser != null) {
            readUsersDataFromFile();
        }
    }
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
    public boolean createUser(JSONObject userData) {
        String username = userData.get(API.USERNAME_KEY).toString();
        String password = userData.get(API.PASSWORD_KEY).toString(); // Encrypted password

        File userFile = new File(getUserFilePath(username)); // Create a temporal file for user
        // Check if user exists
        if (!userFile.exists()) {
            // Create new user
            loggedInUser = new User(username, password);
            // Setup default values
            categories.clear();
            items.clear();
            // Create default categories
            categories.add("Email"); categories.add("Pénzügyek"); categories.add("Egyéb");
            // Create a file for the new user
            writeUserDateToFile();
            return true;
        } else {
            return false; // If the username.json file exists, then return false, meaning the username already exists
        }
    }
    public void saveAll() {
        writeUserDateToFile();
    }


    // == Category ==
    public HashSet<String> getCategoryList() {
        return categories;
    }
    public boolean addNewCategory(String newCategory) {
        if (!categories.contains(newCategory)) {
            categories.add(newCategory);
            return true;
        }
        return false;
    }
    public boolean modifyCategory(String oldCategory, String newCategory) {
        if (categories.remove(oldCategory)) {
            return categories.add(newCategory); // Successful category replacement
        } else {
            return false; // Old category not found (This is not a likely branch, but can happen)
        }
    }
    public boolean removeCategory(String categoryToRemove) {
        return categories.remove(categoryToRemove);
    }


    // == Item ==
    public Item getItem(int index){
        return items.get(index);
    }
    public ArrayList<Item> getItemList() {
        return items;
    }

    // == Temporal item ==
    public Item newTemporalItem() {
        tempItem = new Item();
        return tempItem;
    }
    public Item getTemporalItem() {
        return tempItem;
    }
    public Item setTemporalItem(Item itemReference) {
        return tempItem = itemReference;
    }
    public boolean saveTemporalItem() {
        if (tempItem.getTitle().isBlank() || tempItem.getCategory().getValue().isBlank()) {
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
