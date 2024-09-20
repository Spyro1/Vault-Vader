package backend;

import backend.item.Item;
import frontend.ui.LoginUI;
import frontend.ui.MainUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class API {

    // == Login / Register Methods ==

    /**
     * Create a login request to the controller object with the given user info.
     * @JOSNkeys "username", "password"
     * @param userData JSON object containing "username" and "password" keys.
     * @return True: if username and password is correct, False: if the password is incorrect.
     * @throws Exception Thrown, if the username does not exist.
     */
    static public boolean loginRequest(JSONObject userData) throws Exception {
        if(Controller.INSTANCE.checkUser(userData)){
//            JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!");
            Controller.INSTANCE.loadUser(); // loads the logged in user's data from file
            new MainUI();
            return true;
        }
        return false;
    }

    /**
     * Create a register request to the controller object with the given user info.
     * @JSONkeys "username", "password"
     * @param userData JSON object containing "username" and "password" keys.
     * @return True: if successfully registered, False: if username already exist.
     * @throws Exception Thrown, if there was any problem with the files.
     */
    static public boolean registerRequest(JSONObject userData) throws Exception {
        return Controller.INSTANCE.createUser(userData);
    }

    public static void logoutRequest() throws IOException {
        saveAllChanges();
        new LoginUI();
    }
    // == Item Methods ==

    /**
     *
     * @JSONkeys "title", "category", "fields": []
     * @param itemData
     * @return
     */
    static public boolean addNewItem(JSONObject itemData) {
        return false;
    }

    /**
     *
     * @JSONkeys
     * @param itemData
     * @return
     */
    static public boolean saveItem(JSONObject itemData) {
        return false;
    }

    /**
     *
     * @JSONkeys
     * @param itemData
     * @return
     */
    static public boolean removeItem(JSONObject itemData) {
        return false;
    }
    // == Category Methods ==
    static public boolean addNewCategory(JSONObject categoryData) {
        if (!categoryData.get("category").toString().isEmpty()) {
            return Controller.INSTANCE.addNewCategory(categoryData.get("category").toString());
        }
        return false;
    }

    /**
     *
     * @JSONkeys "category", "oldCategory"
     * @param categoryData
     * @return
     */
    static public boolean modifyCategory(JSONObject categoryData) {
        return true;
    }
    static public boolean removeCategory(JSONObject categoryData) {
        if (!categoryData.get("category").toString().isEmpty()) {
            return Controller.INSTANCE.removeCategory(categoryData.get("category").toString());
        }
        return  false;
    }
//    static public boolean modifyCategory(int categoryId, String name, Color color){
//        return false;
//    }
    // == Item Field Methods ==
    static public boolean addItemField(int itemId, int fieldId, int fieldType){
        return false;
    }
    static public boolean modifyItemField(int itemId, int fieldId, String name){
        return false;
    }
    static public boolean removeItemField(int itemId, int fieldId){
        return false;
    }

    // == get methods ==
    static public ArrayList<Item> getItemList(JSONObject filter){
        if (filter == null){
            ArrayList<Item> itemList = Controller.INSTANCE.getItemList();
            return itemList;
        }
//        else {
//        TODO: Lesz itt valami filteres cucc
//        }
        return null;
    }
    static public ArrayList<String> getCategoryList(){
//        System.out.println(Controller.INSTANCE.getCategoryList().toString());
        return Controller.INSTANCE.getCategoryList();
    }
    static public JSONArray getFieldList(){
        return null;
    }

    public static void saveAllChanges() throws IOException {
        Controller.INSTANCE.saveAll();
    }


}
