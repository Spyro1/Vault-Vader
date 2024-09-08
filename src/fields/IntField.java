package fields;

public class IntField extends Field {
    protected int value;

    public IntField(String fieldName, int value) {
        super(fieldName);
        this.value = value;
    }

    public int getValue() { return this.value; }
    public void setValue(int value) { this.value = value; }
}
