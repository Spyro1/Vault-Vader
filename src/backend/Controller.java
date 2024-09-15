package backend;

import backend.item.Item;
import backend.user.User;
import backend.category.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;

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


    private Controller() {

    }

    private void readUsersDataFromFile() throws IOException, ParseException {
        JSONObject usersData = (JSONObject) new JSONParser().parse(new FileReader(loggedInUser.getName() + ".json"));

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
        pw.write(json.toJSONString());
        pw.flush();
        pw.close();
    }
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

    // API called functions
    public boolean checkUser(JSONObject userData) throws Exception {
        String username = userData.get("username").toString();
        String password = userData.get("password").toString();

        JSONObject userDataFromFile;
        try{
            userDataFromFile = (JSONObject) new JSONParser().parse(new FileReader("users/" + username + ".json"));
        } catch (Exception e){
            throw new Exception("Nem található ilyen nevű felhasználó!\nKérem regisztráljon, vagy lépjen be más felhasználóval.");
        }
        String cryptedPassword = userDataFromFile.get("password").toString();
        if (cryptedPassword.equals(encryptText(password, username))) {
            loggedInUser = new User(username, cryptedPassword);
            return true;
        }
        return false;
    }
    public boolean createUser(JSONObject userData) throws Exception {
        try {
            FileReader fr = new FileReader("users/" + userData.get("username").toString() + ".json"); // Try to search for username.json --> if does not exist, then an exception is thrown.
            fr.close();
            return false;
        } catch (Exception e) {
            loggedInUser = new User(userData.get("username").toString(), encryptText(userData.get("password").toString(), userData.get("username").toString()));
            writeUserDateToFile();
        }
        return true;
    }
}
