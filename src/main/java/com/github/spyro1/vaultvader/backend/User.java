package com.github.spyro1.vaultvader.backend;

import com.github.spyro1.vaultvader.api.API;
import com.github.spyro1.vaultvader.api.JSONSerializable;
import org.json.simple.JSONObject;

public class User implements JSONSerializable {
    String name;
    String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {return password;}
    public String getName() {return name;}

    @Override
    public JSONObject toJSON() {
        JSONObject json = new JSONObject();
        json.put(API.USERNAME_KEY, name);
        json.put(API.PASSWORD_KEY, password);
        return json;
    }

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

