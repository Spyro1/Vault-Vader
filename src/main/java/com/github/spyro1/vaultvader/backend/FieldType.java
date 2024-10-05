package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;

public enum FieldType  {
    TEXT,
    PASS,
    CATEGORY;

    public static FieldType fromString(String t) {
        return switch (t) {
            case API.TEXT_TYPE -> TEXT;
            case API.PASS_TYPE -> PASS;
            case API.COMBO_TYPE -> CATEGORY;
            default -> null;
        };
    }
    public String toString() {
        return switch (this) {
            case TEXT -> API.TEXT_TYPE;
            case PASS -> API.PASS_TYPE;
            case CATEGORY -> API.COMBO_TYPE;
        };
    }
}
