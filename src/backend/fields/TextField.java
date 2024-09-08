package backend.fields;

public class TextField extends Field {
    protected String text;

    public TextField(String fieldName, String text) {
        super(fieldName);
        this.text = text;
    }

    public String getText() { return text; }
    public void setText(String text) { this.text = text; }

    public String toString() {
        return "{\"TextField\":[\"" + super.fieldName + "\",\"" + text + "\"]}";
    }
}
