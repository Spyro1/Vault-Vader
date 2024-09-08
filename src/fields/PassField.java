package fields;

public class PassField extends TextField {

    public PassField(String fieldName, String secret) {
        super(fieldName, "");
        super.text = encryptText(secret);
    }

    public String getDecryptedText() { return decryptText(super.text); }

    @Override
    public void setText(String text) { this.text = encryptText(text); }

    private String encryptText(String text) {
        // TODO: Write Encryption method or use a library
        return "Encrypted Password";
    }
    private String decryptText(String text) {
        // TODO: Write Decyrption method or use a library
        return "Decrypted Password";
    }
}
