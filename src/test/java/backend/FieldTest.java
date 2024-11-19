package backend;

import com.github.spyro1.vaultvader.backend.*;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {
    
    private Field field;
    
    @Before
    public void setup() {
        field = new Field();
    }
    
    @Test
    public void testDefaultConstructor() {
        assertNotNull(field);
        assertEquals("", field.getFieldName());
        assertEquals("", field.getValue());
        assertEquals(FieldType.TEXT, field.getType());
    }
    
    @Test
    public void testConstructorWithParams() {
        field = new Field("TestField", "TestValue", FieldType.PASS);
        assertEquals("TestField", field.getFieldName());
        assertEquals("TestValue", field.getValue());
        assertEquals(FieldType.PASS, field.getType());
    }
    
    @Test
    public void testSetAndGetFieldName() {
        field.setFieldName("FieldName");
        assertEquals("FieldName", field.getFieldName());
    }
    
    @Test
    public void testSetAndGetValue() {
        field.setValue("NewValue");
        assertEquals("NewValue", field.getValue());
    }
    
    @Test
    public void testToJSON() {
        field = new Field("TestField", "TestValue", FieldType.TEXT);
        JSONObject json = field.toJSON();
        assertEquals("TestField", json.get("fieldName"));
        assertEquals("TestValue", json.get("value"));
        assertEquals("text", json.get("type"));
    }
    
    @Test
    public void testFromJSON() {
        JSONObject json = new JSONObject();
        json.put("fieldName", "FieldName");
        json.put("value", "Value");
        json.put("type", "text");
        
        field.fromJSON(json);
        assertEquals("FieldName", field.getFieldName());
        assertEquals("Value", field.getValue());
        assertEquals(FieldType.TEXT, field.getType());
    }
    
    @Test
    public void testToString() {
        field = new Field("TestField", "TestValue", FieldType.TEXT);
        assertEquals("TestField: TestValue [text]", field.toString());
    }
}
