package backend;

import backend.fields.Field;
import backend.item.Item;
import backend.user.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Controller {

    /** Singleton instance of the class */
    public static final Controller INSTANCE = new Controller();

    /** Stores the current user's password items */
    private LinkedList<Item> items = new LinkedList<>();

    /** Stores the current user's categories */
    private ArrayList<String> categories = new ArrayList<>(); // new HashSet<>();

    /** The name of the user currently logged in to the app */
    private User loggedInUser;

    /** Not accessible constructor */
    private Controller() {}

    // === Read / Write user data functions ===
    private void readUsersDataFromFile() throws IOException, ParseException {
        categories.clear();
        items.clear();
        JSONObject usersData = (JSONObject) new JSONParser().parse(new FileReader("users/" + loggedInUser.getName() + ".json"));
        JSONArray categoryArray = (JSONArray) usersData.get("categories");
        for (Object categoryObj : categoryArray) {
            categories.add(categoryObj.toString());
        }
        JSONArray itemArray = (JSONArray) usersData.get("items");
        for (Object itemObj : itemArray) {
            items.add(new Item().fromJSON((JSONObject) itemObj));
        }
    }
    private void writeUserDateToFile() throws IOException {
        JSONObject json = new JSONObject();
        // Build json
        json.put("username", loggedInUser.getName());
        json.put("password", loggedInUser.getPassword());
        JSONArray categoryArray = new JSONArray();
        categoryArray.addAll(categories);
        json.put("categories", categoryArray);
        json.put("items", items.stream().map(Item::toJSON).collect(Collectors.toList()));
        // Write out to file
        PrintWriter pw = new PrintWriter(new FileWriter("users/" + loggedInUser.getName() + ".json"));
        pw.write(json.toJSONString().replace(",", ",\n").replace("{", "{\n").replace("[", "[\n"));
        pw.flush();
        pw.close();
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

    public void loadUser() throws IOException, ParseException {
        if (loggedInUser != null) {
            readUsersDataFromFile();
        }
    }
    public boolean checkUser(JSONObject userData) throws Exception {
        String username = userData.get("username").toString();
        String password = userData.get("password").toString(); // Encrypted password

        JSONObject userDataFromFile;
        try{
            userDataFromFile = (JSONObject) new JSONParser().parse(new FileReader("users/" + username + ".json"));
        } catch (Exception e){
            throw new Exception("Nem található ilyen nevű felhasználó!\nKérem regisztráljon, vagy lépjen be más felhasználóval."); // Throw exception if the user does not exists
        }
        String encryptedPassword = userDataFromFile.get("password").toString();
        if (encryptedPassword.equals(password)) {
            loggedInUser = new User(username, encryptedPassword);
            return true; // True, if the user exits and the password is correct
        }
        return false; // False, if the user exists, but the password is incorrect
    }
    public boolean createUser(JSONObject userData) throws Exception {
        try {
            // Try to search for username.json --> if it does not exist, then an exception is thrown.
            FileReader fr = new FileReader("users/" + userData.get("username").toString() + ".json");
            fr.close();
            return false; // If the username.json file exists, then return false, meaning the username already exists
        } catch (Exception e) {
            // Create new user
            loggedInUser = new User(userData.get("username").toString(), userData.get("password").toString());
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

    public Collection<String> getCategoryList() {
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
        if (!categories.contains(newCategory)) {
            return categories.set(categories.indexOf(oldCategory), newCategory).equals(oldCategory);
        }
        return false;
    }

    public boolean removeCategory(String categoryToRemove) {
        return categories.remove(categoryToRemove);
    }

    public void saveAll() throws IOException {
        writeUserDateToFile();
    }

    public Item getItem(int index){
        return items.get(index);
    }

    public LinkedList<Item> getItemList() {
        return items;
    }

    public void addNewField(JSONObject fieldData, int selectedItemIndex) {
        items.get(selectedItemIndex).getFields().add(new Field().fromJSON(fieldData));
    }
}
