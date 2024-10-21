package backend;

import com.github.spyro1.vaultvader.backend.Item;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    
    Item i = new Item();
    
    @BeforeAll
    public static void setup(){
    
    }
    
    @Test
    public void test() {
        assertNull(i.getTitle(), "Title wrong!");
        assertEquals(0, i.getID(), "ID wrong!");
        
    }
}
