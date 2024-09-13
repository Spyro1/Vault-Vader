package backend.user;

import backend.JSONSerializable;
import org.json.simple.JSONObject;

public class User implements JSONSerializable {
    String name = "";
    String password = "";

    public User(String name, String password) {
        this.name = name;
        this.password = encryptText(password, name);
    }

    public String getPassword() {return password;}
    public String getName() {return name;}
    public String getDecryptedPassword() { return decryptText(password, name);}

    public boolean checkPassword(String passwordToCheck) {
        return password.equals(encryptText(passwordToCheck, name));
    }

    private String encryptText(String text, String key) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < text.length(); i++){
            char res = (char) (text.charAt(i) ^ key.charAt(i % key.length()));
            result.append(res);
        }
        return result.toString();
    }
    private String decryptText(String text, String key) {
        return encryptText(text,key);
    }

    @Override
    public JSONObject toJSON() {
        // TODO: Write toJSON in User
        return null;
    }

    @Override
    public User fromJSON(JSONObject json) {
        // TODO: Write fromJSON in User
        return this;
    }
}

