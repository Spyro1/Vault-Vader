# Vault-Vader
> Created by Marton Szenes, alias Spyro  
> Last update: 2024.09.18.

This is a Password Manager application which is written in Java with Swing.

# Images

> ![Login form](docs/imgs/login.png)  
> *Login screen*

> ![Main window](docs/imgs/main.png)  
> *Main screen*

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
            - refresh() void
            - createItemJList(itemList: List~Item~) List~JList~
        }
    }
    namespace backend {
            
        class Controller{
             <<singleton>>
             + INSTANCE : Controller$
             - items : List~Item~
             - categories : Set~String~
             - loggedInuser : User
             - Controller()
             + loadUser() void
             + checkUser(userData: JSON) boolean
             + createUser(userData: JSON) boolean
             + getCategoryList() List~String~
             + addNewCategory(newCategory: String) bool
             + removeCategory(categoryName: String) bool
             + getItemList() List~Item~
             + saveAll() void
             - readUserDataFromFile() void
             - writeUserDataToFile() void
             - encryptText(text: String, key: String) String$
%%             - decryptText(text: String, key: String) String
        }
        class User{
            - name : String
            - password : String
            + getName() String
            + getPassword() : String
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
%%        class IntField{
%%            - value : int
%%            + getValue() int
%%            + setValue() void
%%        }
%%        class TextField{
%%            - text : String
%%            + getText() String
%%            + setText(text: String) void
%%        }
%%        class PassField{
%%            + getDecryptedText() String
%%            + setText(secret: String) void @Override
%%            - encryptText(text: String) String
%%            - decryptText(text: String) String
%%        }
        
    }
%%    namespace api{
        
    class API{
        + loginRequest(userData: JSON) bool$
        + registerRequest(userData: JSON) bool$
        + logoutRequest() void$
        + getItemList(filter: JSON) List~Item~$ 
        + getCategoryList() List~String~ JSON$
        + saveAllChanges() void$
    }
%%    }
    
    Controller o-- User
    Controller o-- Item
    
%%    TextField <|-- PassField
    Item *-- Field
%%    Field <|-- TextField
%%    Field <|-- IntField
    
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


## API request sequences

### Login Request 

```mermaid
sequenceDiagram
    LoginUI ->> API: loginRequest(): send <br> user data for login
    API ->> Controller: checkUser(): Ask Controller if user exists
    Controller ->> API: Returns True / False indicating if the <br> password is correct, or Throws <br> exception if user does not exists
    API -->> Controller: loadUser(): Ask Controller <br> to load user's credentials
%%    Controller ->> API
    API ->>  LoginUI: Returns checkUser()'s value. If <br> it was True, then Calls MainUI 
```

### Register Request

```mermaid
sequenceDiagram
    LoginUI ->> API: registerRequest(): send user <br> data to register
    API ->> Controller: createUser(): Ask Controller <br> to create the user
    Controller ->> API: Returns True if the user has been <br> successfully created, or false if <br> this username already exists.
    API ->> LoginUI: Returns createUser()'s value
```
