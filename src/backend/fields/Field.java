package backend.fields;

public abstract class Field {
    protected String fieldName;

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() { return fieldName; }
}
