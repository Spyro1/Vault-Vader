package com.github.spyro1.vaultvader.backend;

public class User/* implements JSONSerializable*/ {
    String name;
    String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getPassword() {return password;}
    public String getName() {return name;}

//    @Override
//    public JSONObject toJSON() {
//        JSONObject json = new JSONObject();
//        json.put("name", name);
//        json.put("password", password);
//        return json;
//    }
//
//    @Override
//    public User fromJSON(JSONObject json) {
//        name = json.get("name").toString();
//        password = json.get("password").toString();
//        return this;
//    }
}

