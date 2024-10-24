package backend;

import com.github.spyro1.vaultvader.backend.User;
import com.github.spyro1.vaultvader.api.API;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    
    private User user;
    
    @BeforeEach
    public void setup() {
        user = new User("testUser", "encryptedPassword123");
    }
    
    @Test
    public void testConstructor() {
        assertEquals("testUser", user.getName());
        assertEquals("encryptedPassword123", user.getPassword());
    }
    
    @Test
    public void testGetName() {
        assertEquals("testUser", user.getName());
    }
    
    @Test
    public void testGetPassword() {
        assertEquals("encryptedPassword123", user.getPassword());
    }
    
    @Test
    public void testToJSON() {
        JSONObject json = user.toJSON();
        assertEquals("testUser", json.get(API.USERNAME_KEY));
        assertEquals("encryptedPassword123", json.get(API.PASSWORD_KEY));
    }
    
    @Test
    public void testFromJSON_Valid() {
        JSONObject json = new JSONObject();
        json.put(API.USERNAME_KEY, "newUser");
        json.put(API.PASSWORD_KEY, "newPassword");
        
        user.fromJSON(json);
        assertEquals("newUser", user.getName());
        assertEquals("newPassword", user.getPassword());
    }
    
    @Test
    public void testFromJSON_Invalid() {
        JSONObject json = new JSONObject();
        json.put(API.USERNAME_KEY, "newUser");
        // Missing password field
        
        User result = user.fromJSON(json);
        assertNull(result);
    }
}
