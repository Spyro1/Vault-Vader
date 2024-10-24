package backend;

import com.github.spyro1.vaultvader.backend.FieldType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FieldTypeTest {
    
    @Test
    public void testFromString() {
        assertEquals(FieldType.TEXT, FieldType.fromString("text"));
        assertEquals(FieldType.PASS, FieldType.fromString("pass"));
        assertEquals(FieldType.CATEGORY, FieldType.fromString("combo"));
        assertNull(FieldType.fromString("invalid"));
    }
    
    @Test
    public void testToString() {
        assertEquals("text", FieldType.TEXT.toString());
        assertEquals("pass", FieldType.PASS.toString());
        assertEquals("combo", FieldType.CATEGORY.toString());
    }
}
