# Vault-Vader
This is a Password Manager application which is written in Java with Swing.


# UML - diagram

```mermaid
classDiagram
    class VaultVader{
        + main()
    }
    class GUI{
        + GUI()
    }
    class Item{
        - fileds: List<Field>
        - category: Category
    }
    class Category{
        + categoryName : String
        + color : Color
    }
    class Field{
        - fieldName : String
        + getFieldName() String
    }
    class IntField{
        - value : int
        + getValue() int
        + setValue() void
    }
    class TextField{
        - text : String
        + getText() String
        + setText(text: String) void
    }
    class PassField{
        + getDecryptedText() String
        + setText(secret: String) void @Override
        - encryptText(text: String) String
        - decryptText(text: String) String
    }
    VaultVader --> GUI 
    
    Item *-- Field
    Item *-- Category
   
    Field <|-- IntField
    Field <|-- TextField
    TextField <|-- PassField

```