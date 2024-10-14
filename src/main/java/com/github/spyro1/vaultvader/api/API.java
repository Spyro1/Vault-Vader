package com.github.spyro1.vaultvader.api;

import com.github.spyro1.vaultvader.backend.Controller;
import com.github.spyro1.vaultvader.backend.Item;
import com.github.spyro1.vaultvader.frontend.ui.LoginUI;
import com.github.spyro1.vaultvader.frontend.ui.MainUI;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * The API class provides a centralized interface for interacting with various aspects of the application,
 * including login/registration, item management, category management, and temporal item handling.
 *
 * @author Spyro1
 * @version 1.0
 */
public class API {

    /**
     * Constant strings representing various data keys used in the application.
     */
    public static final String
            USERNAME_KEY = "username",
            PASSWORD_KEY = "password",
            CATEGORY_KEY = "category",
            NEW_CATEGORY_KEY = "newCategory",
            OLD_CATEGORY_KEY = "oldCategory",
            TITLE_KEY = "title",
            FIELDS_KEY = "fields",
            ID_KEY = "id",
            FILTER_KEY = "filter",
            ICON_KEY = "icon",
            ITEMS_KEY = "items",
            TYPE_KEY = "type",
            FIELD_NAME_KEY = "fieldName",
            VALUE_KEY = "value",
            TEXT_TYPE = "text", PASS_TYPE = "pass", COMBO_TYPE = "combo";

    /**
     * The MainUI window object created after login
     */
    public static MainUI mainUI;
    // == Login / Register Methods ==

    /**
     * Create a login request to the controller object with the given user info.
     *
     * @param userData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if username and password is correct, False: if the password is incorrect.
     * @throws Exception Thrown, if the username does not exist.
     * @JSONkeys "username", "password"
     */
    static public boolean loginRequest(JSONObject userData) throws Exception {
        if ( userData.containsKey(USERNAME_KEY) && userData.containsKey(PASSWORD_KEY) && Controller.INSTANCE.checkUser(userData) ) {
            Controller.INSTANCE.loadUser(); // loads the logged-in user's data from file
            mainUI = new MainUI();
            return true;
        }
        return false;
    }

    /**
     * Create a register request to the controller object with the given user info.
     *
     * @param userData JSON object containing the necessary fields given in JSONkeys section
     * @JSONkeys "username", "password"
     * @return True: if successfully registered, False: if username already exist.
     * @throws Exception Thrown, if there was any problem with the files.
     */
    static public boolean registerRequest(JSONObject userData) throws Exception {
        return Controller.INSTANCE.createUser(userData);
    }

    /**
     * Create a request to log out the user and save all data to the user's file.
     */
    public static void logoutRequest() {
        saveAllChanges();
        new LoginUI(); // Go back to log in ui
    }

    // == Item Methods ==

    /**
     * Request to save the specified item data.
     * @JSONkeys "icon", "title", "category", "fields"
     * @param itemData A JSON object containing the item data.
     * @return True if the item is saved successfully, false otherwise.
     * @throws Exception If an error occurs during the save process.
     */
    static public boolean saveItem(JSONObject itemData) throws Exception {
        API.getTemporalItem().fromJSON(itemData); // Set temporal item with the given item data
        return Controller.INSTANCE.saveTemporalItem();
    }

    /**
     * Removes the searched title given in the itemData parameter. If the parameter is null, then it removes the temporal item from the list.
     *
     * @param itemData JSON object containing the necessary fields given in JSONkeys section
     * @return True if the item is removed successfully, false otherwise.
     * @JSONkeys "title"
     */
    public static boolean removeItem(JSONObject itemData) {
        if ( itemData == null ) { // Remove temporal item
            return getItemList(null).removeIf(x -> x.getID() == API.getTemporalItem().getID());
        } else if (itemData.containsKey(API.TITLE_KEY)) { // Removed searched item based on title match
            return getItemList(null).removeIf(x -> x.getTitle().equals(itemData.get(API.TITLE_KEY).toString()));
        }
        return false;
    }

    /**
     * Retrieves a list of items based on the specified filter.
     *
     * @param filter A JSON object containing filter criteria, if null, then the whole item list is returned.
     * @return A list of items matching the filter.
     * @JSONkeys "filter"
     */
    static public ArrayList<Item> getItemList(JSONObject filter) {
        // Show all items = no filter
        if ( filter == null ) {
            return Controller.INSTANCE.getItemList();
        }
        // TODO: Write filter for get Item list, for search and category selection
        return null;
    }

    /**
     * Retrieves the item at the specified index.
     *
     * @param itemIndex The index of the item to retrieve.
     * @return The {@link Item} at the specified index.
     */
    public static Item getItemData(int itemIndex) {
        return Controller.INSTANCE.getItem(itemIndex);
    }

    // == Temporal item's methods ==

    /**
     * Creates a new temporal item.
     *
     * @return A new temporal item.
     */
    static public Item newTemporalItem() {
        return Controller.INSTANCE.newTemporalItem();
    }

    /**
     * Retrieves the current temporal item managed by the Controller.
     *
     * @return the current temporal item of type {@link Item}.
     */
    public static Item getTemporalItem() {
        return Controller.INSTANCE.getTemporalItem();
    }

    /**
     * Sets the temporal item to a specified reference managed by the Controller.
     *
     * @param itemReference the {@link Item} instance to be set as the temporal item.
     * @return the given item reference of type {@link Item} so the function can be chained.
     */
    public static Item setTemporalItem(Item itemReference) {
        return Controller.INSTANCE.setTemporalItem(itemReference);
    }

    // == Category Methods ==

    /**
     * Adds a new category.
     *
     * @param categoryData A JSON object containing the category name.
     * @return True if the category is added successfully, false otherwise.
     * @JSONkeys "category"
     */
    static public boolean addNewCategory(JSONObject categoryData) {
        if ( categoryData.containsKey(CATEGORY_KEY) && !categoryData.get(CATEGORY_KEY).toString().isBlank() ) {
            return Controller.INSTANCE.addNewCategory(categoryData.get(CATEGORY_KEY).toString());
        }
        return false;
    }

    /**
     * Request to modify the category title with the given new and old titles.
     *
     * @param categoryData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if the modification was successful, False: otherwise
     * @JSONkeys "newCategory", "oldCategory"
     */
    static public boolean modifyCategory(JSONObject categoryData) {
        if ( categoryData.containsKey(NEW_CATEGORY_KEY) && categoryData.containsKey(OLD_CATEGORY_KEY) && !categoryData.get(NEW_CATEGORY_KEY).toString().isBlank() && !categoryData.get(OLD_CATEGORY_KEY).toString().isBlank() ) {
            return Controller.INSTANCE.modifyCategory(categoryData.get(OLD_CATEGORY_KEY).toString(), categoryData.get(NEW_CATEGORY_KEY).toString());
        }
        return false;
    }

    /**
     * Request to remove the selected category with the given title.
     *
     * @param categoryData JSON object containing the necessary fields given in JSONkeys section
     * @return True: if the removal is successful, False: if the field was empty or otherwise
     * @JSONkeys "category"
     */
    static public boolean removeCategory(JSONObject categoryData) {
        if ( categoryData.get(CATEGORY_KEY) != null && !categoryData.get(CATEGORY_KEY).toString().isBlank() ) {
            return Controller.INSTANCE.removeCategory(categoryData.get(CATEGORY_KEY).toString());
        }
        return false;
    }

    /**
     * Retrieves a list of all categories.
     *
     * @return A set of category names.
     */
    public static HashSet<String> getCategoryList() {
        return Controller.INSTANCE.getCategoryList();
    }

    // == Other methods ==

    /**
     * Encrypts the given data using the specified key.
     *
     * @param data The data to encrypt.
     * @param key The encryption key.
     * @return The encrypted data.
     */
    public static String encryptData(String data, String key) {
        return Controller.encryptText(data, key);
    }

    /**
     * Decrypts the given data using the specified key.
     *
     * @param data The encrypted data.
     * @param key The decryption key.
     * @return The decrypted data.
     */
    public static String decryptData(String data, String key) {
        return Controller.decryptText(data, key);
    }

    /**
     * Saves all changes made to items and categories, ensuring data persistence.
     */
    public static void saveAllChanges() {
        Controller.INSTANCE.saveAll();
    }
}
