package com.github.spyro1.vaultvader.backend.fields;

import com.github.spyro1.vaultvader.backend.API;

public enum FieldType {
    TEXT,
    INT,
    PASS,
    CATEGORY;

    public static FieldType fromString(String t) {
        switch (t) {
            case API.TEXT_TYPE: return TEXT;
            case API.PASS_TYPE: return PASS;
            case API.CATEGORY_TYPE: return CATEGORY;
            default: return null;
        }
    }
    public String toString() {
        switch (this) {
            case TEXT: return API.TEXT_TYPE;
            case PASS: return API.PASS_TYPE;
            case CATEGORY: return API.CATEGORY_TYPE;
            default: return null;
        }
    }
}
