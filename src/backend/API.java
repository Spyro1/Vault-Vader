package backend;

import backend.category.Category;
import backend.fields.Field;
import backend.item.Item;
import backend.user.User;
import frontend.MainUI;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class API {

    // == Login / Register Methods ==
    static public boolean loginRequest(JSONObject userData) {
        // TODO: Adatbázisban felhasználó ellenőrzés
        if(Controller.INSTANCE.checkUser(userData)){
//            JOptionPane.showMessageDialog(null, "Sikeres bejelentkezés!");
            new MainUI();
            return true;
        }
        return false;
    }
    static public boolean registerRequest(JSONObject userData) {
        return true;
    }
    // == Item Methods ==
    static public boolean addItem(int itemId){
        return false;
    }
    static public boolean modifyItem(int itemId, String title, Category category){
        return false;
    }
    static public boolean removeItem(int itemId){
        return false;
    }
    // == Category Methods ==
    static public boolean addCategory(int categoryId, String name, Color color){
        return false;
    }
    static public boolean modifyCategory(int categoryId, String name, Color color){
        return false;
    }
    static public boolean removeCategory(int categoryId){
        return false;
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
    static public JSONArray getItemList(){
        return null;
    }
    static public JSONArray getCategoryList(){
        return null;
    }
    static public JSONArray getFieldList(){
        return null;
    }


}
