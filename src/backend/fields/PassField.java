package backend.fields;

import org.json.simple.JSONObject;

public class PassField extends TextField {

    public PassField(String fieldName, String secret) {
        super(fieldName, "");
        super.text = encryptText(secret, secret);
    }

    public String getDecryptedText() { return decryptText(super.text, super.text); }

    @Override
    public void setText(String text) { this.text = encryptText(text, ""); }

    private String encryptText(String text, String key) {

        // TODO: Write Encryption method or use a library
        return "Encrypted Password";
    }
    private String decryptText(String text, String key) {
        // TODO: Write Decyrption method or use a library
        return "Decrypted Password";
    }
    public String toString() {
        return "{Type: PassField, FieldName: " + super.fieldName + ", Value: " + text + "}";
    }
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", "PassField");
        obj.put("fieldName", super.fieldName);
        obj.put("value", super.text);
        return obj;
    }
}
