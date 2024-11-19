package backend;

import com.github.spyro1.vaultvader.backend.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ItemTest {
    
    private Item item;
    
    @Before
    public void setup() {
        item = new Item();
    }
    
    @Test
    public void testDefaultConstructor() {
        assertNotNull(item);
        assertEquals("picture.png", item.getIcon());
        assertNull(item.getTitle());
        assertEquals("Kategória", item.getCategory().getFieldName());
        assertEquals(2, item.getFields().size());
    }
    
    @Test
    public void testSetAndGetIcon() {
        item.setIcon("newIcon.png");
        assertEquals("newIcon.png", item.getIcon());
    }
    
    @Test
    public void testSetAndGetTitle() {
        item.setTitle("New Title");
        assertEquals("New Title", item.getTitle());
    }
    
    @Test
    public void testSetAndGetCategory() {
        Field newCategory = new Field("New Category", "Value", FieldType.CATEGORY);
        item.setCategory(newCategory);
        assertEquals(newCategory, item.getCategory());
    }
    
    @Test
    public void testAddField() {
        Field newField = new Field("ExtraField", "value", FieldType.TEXT);
        item.addField(newField);
        assertTrue(item.getFields().contains(newField));
    }
    
    @Test
    public void testToJSON() {
        JSONObject json = item.toJSON();
        assertEquals(item.getIcon(), json.get("icon"));
        assertEquals(item.getTitle(), json.get("title"));
        assertNotNull(json.get("category"));
        assertNotNull(json.get("fields"));
    }
    
    @Test
    public void testFromJSON() throws Exception {
        JSONObject json = new JSONObject();
        json.put("icon", "icon.png");
        json.put("title", "Test Title");
        json.put("category", "Test Category");
        json.put("fields", new JSONArray());
        
        item.fromJSON(json);
        assertEquals("icon.png", item.getIcon());
        assertEquals("Test Title", item.getTitle());
        assertEquals("Test Category", item.getCategory().getValue());
    }
    
    @Test
    public void testToString() {
        item.setTitle("MyItem");
        Field category = new Field("Kategória", "item", FieldType.CATEGORY);
        item.setCategory(category);
        assertEquals("MyItem [item]", item.toString());
    }
    
    @Test
    public void testReset() {
        item.setTitle("Title");
        item.reset();
        assertNull(item.getTitle());
        assertEquals("Kategória", item.getCategory().getFieldName());
        assertEquals(2, item.getFields().size());
    }
}
