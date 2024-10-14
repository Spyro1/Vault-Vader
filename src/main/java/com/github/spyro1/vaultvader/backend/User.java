package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.JSONObject;

/**
 * Represents a User object.
 */
public class User implements JSONSerializable {
    /** The user's name. */
    private String name;

    /** The user's password. */
    private String password;

    /**
     * Creates a new User object with the provided name and encrypted password.
     * @param name the user's name
     * @param password the user's encrypted password
     */
    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Returns the user's encrypted password.
     * @return the user's encrypted password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the user's name.
     * @return the user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Converts the User object into a JSONObject containing the user's name and password.
     * @JSONkeys "username", "password"
     * @return the JSONObject representation of the User object
     */
    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(API.USERNAME_KEY, name);
        json.put(API.PASSWORD_KEY, password);
        return json;
    }

    /**
     * Attempts to create a User object from a provided JSONObject.
     * @param json the JSONObject to convert
     * @JSONkeys "username", "password"
     * @return a new User object if the JSON contains both API.USERNAME_KEY and API.PASSWORD_KEY, otherwise null
     */
    @Override
    public User fromJSON(JSONObject json) {
        if (json.containsKey(API.USERNAME_KEY) && json.containsKey(API.PASSWORD_KEY)) {
            name = json.get(API.USERNAME_KEY).toString();
            password = json.get(API.PASSWORD_KEY).toString();
            return this;
        } else {
            return null;
        }
    }
}