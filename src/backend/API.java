package backend;

import backend.item.Item;
import frontend.ui.LoginUI;
import frontend.ui.MainUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.Collection;

public class API {

    // == Login / Register Methods ==

    /**
     * Create a login request to the controller object with the given user info.
     * @JSONkeys "username", "password"
     * @param userData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if username and password is correct, False: if the password is incorrect.
     * @throws Exception Thrown, if the username does not exist.
     */
    static public boolean loginRequest(JSONObject userData) throws Exception {
        if(Controller.INSTANCE.checkUser(userData)){
//            JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!");
            Controller.INSTANCE.loadUser(); // loads the logged-in user's data from file
            new MainUI();
            return true;
        }
        return false;
    }

    /**
     * Create a register request to the controller object with the given user info.
     * @JSONkeys "username", "password"
     * @param userData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if successfully registered, False: if username already exist.
     * @throws Exception Thrown, if there was any problem with the files.
     */
    static public boolean registerRequest(JSONObject userData) throws Exception {
        return Controller.INSTANCE.createUser(userData);
    }

    /**
     * Create a request to log out the user and save all data to the user's file.
     * @throws IOException Thrown if the user's file can not be opened.
     */
    public static void logoutRequest() throws IOException {
        saveAllChanges();
        new LoginUI(); // Go back to log in ui
    }
    // == Item Methods ==

    /**
     *
     * @JSONkeys "title", "category", "fields": []
     * @param itemData JSON object containing the necessary fields given in JSONkeys section
     * @return
     */
    static public boolean addNewItem(JSONObject itemData) {
        // TODO: Write add new item api function
        return false;
    }

    /**
     *
     * @JSONkeys
     * @param itemData JSON object containing the necessary fields given in JSONkeys section
     * @return
     */
    static public boolean saveItem(JSONObject itemData) {
        // TODO: Write save item api function
        return false;
    }

    /**
     *
     * @JSONkeys
     * @param itemData JSON object containing the necessary fields given in JSONkeys section
     * @return
     */
    static public boolean removeItem(JSONObject itemData) {
        // TODO: Write remove item api function
        return false;
    }
    // == Category Methods ==

    /**
     *
     * @JSONkeys
     * @param categoryData JSON object containing the necessary fields given in JSONkeys section
     * @return
     */
    static public boolean addNewCategory(JSONObject categoryData) {
        if (!categoryData.get("category").toString().isEmpty()) {
            return Controller.INSTANCE.addNewCategory(categoryData.get("category").toString());
        }
        return false;
    }

    /**
     * Request to modify the category title with the given new and old titles.
     * @JSONkeys "newCategory", "oldCategory"
     * @param categoryData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if the modification was successful, False: otherwise
     */
    static public boolean modifyCategory(JSONObject categoryData) {
        // TODO: Write modify category API
        if (!categoryData.get("newCategory").toString().isEmpty() && !categoryData.get("oldCategory").toString().isEmpty()) {
            String oldCategory = categoryData.get("oldCategory").toString();
            String newCategory = categoryData.get("newCategory").toString();
            return Controller.INSTANCE.modifyCategory(oldCategory, newCategory);
        }
        return true;
    }

    /**
     * Request to remove the selected category with the given title.
     * @JSONkeys "category"
     * @param categoryData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if the removal is successful, False: if the field was empty or otherwise
     */
    static public boolean removeCategory(JSONObject categoryData) {
        if (!categoryData.get("category").toString().isEmpty()) {
            return Controller.INSTANCE.removeCategory(categoryData.get("category").toString());
        }
        return  false;
    }
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
    static public Collection<Item> getItemList(JSONObject filter){
        if (filter == null){
            Collection<Item> itemList = Controller.INSTANCE.getItemList();
            return itemList;
        }
//        else {
//        TODO: Lesz itt valami filteres cucc
//        }
        return null;
    }
    static public Collection<String> getCategoryList(){
//        System.out.println(Controller.INSTANCE.getCategoryList().toString());
        return Controller.INSTANCE.getCategoryList();
    }
    static public JSONArray getFieldList(){
        return null;
    }

    public static void saveAllChanges() throws IOException {
        Controller.INSTANCE.saveAll();
    }


//    public static void displayItem(JSONObject json) {
//    }
}
