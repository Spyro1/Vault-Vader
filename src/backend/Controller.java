package backend;

import backend.item.Item;
import backend.user.User;
import backend.category.Category;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    /** Singleton instance of the class */
    public static final Controller INSTANCE = new Controller();

    /** Stores the current user's password items */
    private ArrayList<Item> items = new ArrayList<>();

    /** Stores the current user's categories */
    private ArrayList<Category> categories = new ArrayList<>();


    private final String itemDataJSONFilePath = "data/items.json";
    private final String categoryDataJSONFilePath = "data/categories.json";
    private final String userDataJSONFilePath = "data/users.json";


    private Controller() {

    }

//    private void readUsersDataFile(){
//        JSONObject usersData;
//        try {
//            usersData = (JSONObject) new JSONParser().parse(new FileReader(userDataJSONFilePath));
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
    private void readCategoriesDataFile(){

    }
    private void readItemsDataFile(){

    }
//    private void writeUsersDataFile(){
//        JSONObject usersData = new JSONObject();
//        JSONArray usersArray = new JSONArray();
//        for(User user : users){
//            usersArray.add(user.toJSON());
//        }
//        usersData.put("users", usersArray);
//
//    }
    private void writeCategoriesDataFile(){

    }
    private void writeItemsDataFile(){

    }

    // API called functions
    public boolean checkUser(JSONObject userData) {
        String username = userData.get("username").toString();
        String password = userData.get("password").toString();
        ArrayList<User> users = new ArrayList<>(); // new User(username,password)
        JSONObject usersData;
        try {
            usersData = (JSONObject) new JSONParser().parse(new FileReader(userDataJSONFilePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
