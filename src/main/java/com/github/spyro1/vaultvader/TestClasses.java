package com.github.spyro1.vaultvader;

import com.github.spyro1.vaultvader.backend.Item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestClasses {
    
    Item i = new Item();
    
    @Test
    public void test() {
        assertNull(i.getTitle(), "Title wrong!");
        assertEquals(0, i.getID(), "ID wrong!");
        
    }
}
