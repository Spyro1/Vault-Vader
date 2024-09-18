package backend.fields;

import org.json.simple.JSONObject;

public class PassField extends TextField {

    public PassField() {
        super();
    }
    public PassField(String fieldName, String secret) {
        super(fieldName, secret);
//        super.text = encryptText(secret, secret);
    }

//    public String getDecryptedText() { return decryptText(super.text, super.text); }

    @Override
//    public void setText(String text) { this.text = encryptText(text, ""); }

    public String toString() {
        return "{Type: PassField, FieldName: " + super.fieldName + ", Value: " + text + "}";
    }
    @Override
    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("type", "PassField");
        obj.put("fieldName", super.fieldName);
        obj.put("value", super.text);
        return obj;
    }
}
