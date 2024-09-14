# Vault-Vader
This is a Password Manager application which is written in Java with Swing.


# UML - diagram

```mermaid
classDiagram
    direction TB
    class LoginUI{
        + main()
        + LoginUI()
    }
    class MainUI{
        + MainUI()
    }
    class Controller{
         <<singleton>>
         + INSTANCE : Controller$
        - userManager : UserManager
    }
%%    Controller *-- UserManager
    class User{
        - name : String
        - password : String
    }
    class UserManager{
        - users: List~User~
    }
    UserManager *-- User
    class ItemManager{
        - items : List~Item~
        + addItem(item: Item) void
    }
    class Item{
        - icon : ImageIcon
        - title : String
        - fileds : List~Field~
        - category : Category
    }
    ItemManager *-- Item
    Item *-- Field
    class Category{
        + categoryName : String
        + color : Color
    }
    class CategoryManager{
        - categories : List~Category~
    }
    CategoryManager *-- Category
%%    Item o-- Category
    Category --o Item
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
    Field <|-- TextField
    Field <|-- IntField
    TextField <|-- PassField
    
    class API{
        + loginRequest() bool$
        + registerRequest() bool$
        
    }
    Controller --> UserManager
    Controller --> ItemManager
    Controller --> CategoryManager
    API --> Controller
    LoginUI --> API
    MainUI --> API
    
   

```


## Data storage

JSON file name is the username ->  1 file per user

```json
{
  "username": "username",
  "password": "*x&%645&",
  "categories": [
        "Example category 1", 
        "Example category 2",
        "Example category 3"    
  ],
  "items": [
    {
      "title": "Item Title",
      "category": "Category name",
      "fields": [
        {
          "type" : "TextField / IntField / PassField",
          "fieldName": "Field Name",
          "value": "Value of the field"
        }
      ]
    }
  ]
}
```