package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;

/**
 * The FieldType enum defines the type of field. It supports three types: TEXT, PASS, and CATEGORY. It also provides
 * utility methods for converting between strings and enum values.
 */
public enum FieldType {
    /// Represents a text field type.
    TEXT,
    /// Represents a password field type.
    PASS,
    /// Represents a category or combo-box field type.
    CATEGORY;
    
    /**
     * Converts a string to the corresponding FieldType enum value.
     * @param t The string representing the field type.
     * @return The corresponding FieldType, or null if the string is not valid.
     */
    public static FieldType fromString(String t) {
        return switch (t) {
            case API.TEXT_TYPE -> TEXT;
            case API.PASS_TYPE -> PASS;
            case API.COMBO_TYPE -> CATEGORY;
            default -> null;
        };
    }
    
    /**
     * Converts the FieldType enum value to its corresponding string representation.
     * @return The string representation of the FieldType.
     */
    public String toString() {
        return switch (this) {
            case TEXT -> API.TEXT_TYPE;
            case PASS -> API.PASS_TYPE;
            case CATEGORY -> API.COMBO_TYPE;
        };
    }
}
