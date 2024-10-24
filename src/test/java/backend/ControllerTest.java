package backend;

import com.github.spyro1.vaultvader.backend.Controller;
import com.github.spyro1.vaultvader.backend.Item;
import com.github.spyro1.vaultvader.backend.User;
import com.github.spyro1.vaultvader.api.API;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    
    private static JSONObject validUserData = new JSONObject();
    
    @BeforeEach
    public void setup() {
        validUserData = new JSONObject();
        // Set up valid user data for testing user-related functionalities
        validUserData.put(API.USERNAME_KEY, "testUser");
        validUserData.put(API.PASSWORD_KEY, "encryptedPassword123");
    }
    
    // == User Management ==
    
    @Test
    @Order (1)
    public void testCreateUser_Success() throws Exception {
        // Create a new user with valid data
        if (!Controller.INSTANCE.checkUser(validUserData)){
            assertTrue(Controller.INSTANCE.createUser(validUserData));
        } else {
            assertFalse(Controller.INSTANCE.createUser(validUserData));
        }
        
        // Check if the user data is saved correctly
        User loggedInUser = Controller.INSTANCE.getLoggedInUser();
        assertNotNull(loggedInUser);
        assertEquals("testUser", loggedInUser.getName());
    }
    
    @Test
    @Order(2)
    public void testCheckUser_InvalidPassword() throws Exception {
        Controller.INSTANCE.createUser(validUserData);
        
        // Modify the password in the data
        validUserData.put(API.PASSWORD_KEY, "wrongPassword");
        
        assertFalse(Controller.INSTANCE.checkUser(validUserData));
    }
    
    // == Category Management ==
    
    @Test
    @Order(3)
    public void testAddNewCategory_Success() throws Exception {
        assertTrue(Controller.INSTANCE.addNewCategory("NewCategory"));
        
        // Check if the category list contains the new category
        Set<String> categories = Controller.INSTANCE.getCategoryList();
        assertTrue(categories.contains("NewCategory"));
    }
    
    @Test
    @Order(4)
    public void testAddNewCategory_AlreadyExists() {
        Controller.INSTANCE.addNewCategory("DuplicateCategory");
        assertFalse(Controller.INSTANCE.addNewCategory("DuplicateCategory"));
    }
    
    @Test
    @Order(5)
    public void testModifyCategory_Success() {
        Controller.INSTANCE.addNewCategory("OldCategory");
        
        // Modify category
        assertTrue(Controller.INSTANCE.modifyCategory("OldCategory", "NewCategory"));
        
        // Check if the old category is replaced
        Set<String> categories = Controller.INSTANCE.getCategoryList();
        assertFalse(categories.contains("OldCategory"));
        assertTrue(categories.contains("NewCategory"));
    }
    
    @Test
    @Order(6)
    public void testModifyCategory_NotFound() {
        assertFalse(Controller.INSTANCE.modifyCategory("NonExistentCategory", "NewCategory"));
    }
    
    @Test
    @Order(7)
    public void testRemoveCategory_Success() {
        Controller.INSTANCE.addNewCategory("CategoryToRemove");
        
        // Remove category and verify
        assertTrue(Controller.INSTANCE.removeCategory("CategoryToRemove"));
        assertFalse(Controller.INSTANCE.getCategoryList().contains("CategoryToRemove"));
    }
    
    @Test
    @Order(8)
    public void testRemoveCategory_NotFound() {
        assertFalse(Controller.INSTANCE.removeCategory("NonExistentCategory"));
    }
    
    // == Item Management ==
    
    @Test
    @Order(9)
    public void testNewTemporalItem() {
        Item tempItem = Controller.INSTANCE.newTemporalItem();
        assertNotNull(tempItem);
    }
    
    @Test
    @Order(10)
    public void testSaveTemporalItem_Success() {
        // Create a new temp item, set values, and save
        Item tempItem = Controller.INSTANCE.newTemporalItem();
        tempItem.setTitle("NewItem");
        tempItem.getCategory().setValue("NewCategory");
        
        // Save temp item
        assertTrue(Controller.INSTANCE.saveTemporalItem());
        
        // Check if the item was added to the list
        ArrayList<Item> itemList = Controller.INSTANCE.getItemList();
        assertFalse(itemList.isEmpty());
        assertEquals("NewItem", itemList.getLast().getTitle());
    }
    
    @Test
    @Order(11)
    public void testSaveTemporalItem_BlankFields() {
        Item tempItem = Controller.INSTANCE.newTemporalItem();
        
        // Try saving an item with blank fields
        assertFalse(Controller.INSTANCE.saveTemporalItem());
    }
    
    @Test
    @Order(12)
    public void testGetItem() {
        // Add a temp item and save it
        Item tempItem = Controller.INSTANCE.newTemporalItem();
        tempItem.setTitle("TestItem");
        tempItem.getCategory().setValue("TestCategory");
        Controller.INSTANCE.saveTemporalItem();
        
        // Retrieve the item and verify
        Item retrievedItem = Controller.INSTANCE.getItem(0);
        assertEquals("TestItem", retrievedItem.getTitle());
        assertEquals("TestCategory", retrievedItem.getCategory().getValue());
    }
    
    // == Encryption/Decryption ==
    
    @Test
    @Order(13)
    public void testEncryptDecrypt() {
        String text = "SensitiveData";
        String key = "encryptionKey";
        
        // Encrypt the text
        String encrypted = Controller.INSTANCE.encryptText(text, key);
        assertNotEquals(text, encrypted);
        
        // Decrypt the text
        String decrypted = Controller.INSTANCE.decryptText(encrypted, key);
        assertEquals(text, decrypted);
    }
    
    // == File I/O (Basic) ==
    
    @Test
    @Order(14)
    public void testReadWriteUserDataToFile() throws Exception {
        // Create user and write data to file
        Controller.INSTANCE.createUser(validUserData);
        Controller.INSTANCE.checkUser(validUserData);
        Controller.INSTANCE.saveAll();
        
        // Simulate a new login and load data
        Controller.INSTANCE.loadUser();
        
        // Verify if data is correctly loaded
        User loggedInUser = Controller.INSTANCE.getLoggedInUser();
        assertNotNull(loggedInUser);
        assertEquals("testUser", loggedInUser.getName());
        
        // Clean up test files after the test
//        File userFile = new File("users/testUser.json");
//        if (userFile.exists()) {
//            assertTrue(userFile.delete());
//        }
    }
}
