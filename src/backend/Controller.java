package backend;

import backend.item.Item;
import backend.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

public class Controller {

    /**
     * Singleton instance of the class
     */
    public static final Controller INSTANCE = new Controller();

    /**
     * Stores the current user's password items
     */
    private ArrayList<Item> items = new ArrayList<>();

    /**
     * Stores the current user's categories
     */
    private ArrayList<String> categories = new ArrayList<>();

    /**
     * The name of the user currently logged in to the app
     */
    private User loggedInUser;


    private Controller() {}

    // === Read / Write user data functions ===
    private void readUsersDataFromFile() throws IOException, ParseException {
        categories.clear();
        items.clear();
        JSONObject usersData = (JSONObject) new JSONParser().parse(new FileReader("users/" + loggedInUser.getName() + ".json"));
        JSONArray categoryArray = (JSONArray) usersData.get("categories");
        for (int i = 0; i < categoryArray.size(); i++) {
            categories.add(categoryArray.get(i).toString());
        }
        JSONArray itemArray = (JSONArray) usersData.get("items");
        for (Object itemObj : itemArray) {
            items.add(new Item().fromJSON((JSONObject) itemObj));
        }
    }
    private void writeUserDateToFile() throws IOException {
        JSONObject json = new JSONObject();
//        // Build json
        json.put("username", loggedInUser.getName());
        json.put("password", loggedInUser.getPassword());
        JSONArray categoryArray = new JSONArray();
        for (String category : categories) {
            categoryArray.add(category);
        }
        json.put("categories", categoryArray);
        JSONArray itemArray = new JSONArray();
        for (Item item : items) {
            itemArray.add(item.toJSON());
        }
        json.put("items", itemArray);
        // Write out to file
        PrintWriter pw = new PrintWriter(new FileWriter("users/" + loggedInUser.getName() + ".json"));
        pw.write(json.toJSONString().replace(",", ",\n").replace("{", "{\n").replace("[", "[\n"));
        pw.flush();
        pw.close();
    }

    /*public JSONArray collectionToJSON(ArrayList<? extends JSONSerializable>  collection){
        JSONArray itemArray = new JSONArray();
        for (Object o : collection) {
            itemArray.add(((JSONSerializable)o).toJSON());
        }
        return itemArray;
    }*/

    // == Cryption functions ==
    private String encryptText(String text, String key) {
        StringBuilder result = new StringBuilder(); // init string builder object
        // Encrypt every character one by one
        for (int i = 0; i < text.length(); i++){
            char res = (char) (text.charAt(i) ^ key.charAt(i % key.length())); // Make an XOR bitwise from the password and the key
            result.append(res); // Concatenate to the string
        }
        return result.toString();
    }
    private String decryptText(String text, String key) {
        return encryptText(text,key); // Encrypt and Decrypt is symmetric because of the XOR
    }


    // === API called public functions ===

    public void loadUser(){
        if (loggedInUser != null) {
            try {
                readUsersDataFromFile();
            } catch (Exception e){
                JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    public boolean checkUser(JSONObject userData) throws Exception {
        String username = userData.get("username").toString();
        String password = userData.get("password").toString();

        JSONObject userDataFromFile;
        try{
            userDataFromFile = (JSONObject) new JSONParser().parse(new FileReader("users/" + username + ".json"));
        } catch (Exception e){
            throw new Exception("Nem található ilyen nevű felhasználó!\nKérem regisztráljon, vagy lépjen be más felhasználóval."); // Throw exception if the user does not exists
        }
        String cryptedPassword = userDataFromFile.get("password").toString();
        if (cryptedPassword.equals(encryptText(password, username))) {
            loggedInUser = new User(username, cryptedPassword);
            return true; // True, if the user exits and the password is correct
        }
        return false; // False, if the user exists, but the password is incorrect
    }
    public boolean createUser(JSONObject userData) throws Exception {
        try {
            FileReader fr = new FileReader("users/" + userData.get("username").toString() + ".json"); // Try to search for username.json --> if does not exist, then an exception is thrown.
            fr.close();
            return false;
        } catch (Exception e) {
            loggedInUser = new User(userData.get("username").toString(), encryptText(userData.get("password").toString(), userData.get("username").toString()));
            // Setup default values
            categories.clear();
            items.clear();
            categories.add("Email");
            categories.add("Pénzügyek");
            categories.add("Egyéb");
            writeUserDateToFile();
        }
        return true;
    }

    public ArrayList<String> getCategoryList() {
        return categories;
    }

    public boolean addNewCategory(String newCategory) {
        if (!categories.contains(newCategory)) {
            categories.add(newCategory);
            return true;
        }
        return false;
    }

    public boolean removeCategory(String categoryToRemove) {
        return categories.remove(categoryToRemove);
    }

    public void saveAll() throws IOException {
        writeUserDateToFile();
    }

    public ArrayList<Item> getItemList() {
        return items;
    }
}
