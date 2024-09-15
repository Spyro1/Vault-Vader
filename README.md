# Vault-Vader
This is a Password Manager application which is written in Java with Swing.


# UML - diagram

```mermaid
classDiagram
    direction TB
    namespace frontend {
        
        class LoginUI{
            + main()
            + LoginUI()
        }
        class MainUI{
            + MainUI()
        }
    }
    namespace backend {
            
        class Controller{
             <<singleton>>
             + INSTANCE : Controller$
             - items : List~Item~
             - categories : List ~String~
             - loggedInuser : User
             - Controller()
             + ReadUserDataFromFile() void
             + writeUserDataToFile() void
             + checkUser(userData: JSON) boolean
             + createUser(userData: JSON) boolean
             - encryptText(text: String, key: String) String
             - decryptText(text: String, key: String) String
        }
        class User{
            - name : String
            - password : String
        }
        class Item{
            - icon : ImageIcon
            - title : String
            - category : String
            - fileds : List~Field~
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
        
    }
%%    namespace api{
        
    class API{
        + loginRequest(userData: JSON) bool$
        + registerRequest(userData: JSON) bool$
        + getCategoryList() JSON$
    }
%%    }
    Item *-- Field
    Field <|-- TextField
    Field <|-- IntField
    TextField <|-- PassField
    
    Controller *-- User
    Controller *-- Item
    
    
    API --> Controller
    API <-- LoginUI
    API <-- MainUI
%%    LoginUI --> API
%%    MainUI --> API   
   

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