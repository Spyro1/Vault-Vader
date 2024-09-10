import backend.User;
import frontend.LoginUI;

import javax.swing.*;

public class Main extends JFrame {

    private JPanel headerPanel;
    static public void main(String[] args){
        new LoginUI();
    }
    // The program starts here
//    static public void main(String[] args) {
//        new Main();
//        new MainUI();
//        new LoginUI();
//        Item i = new Item();
//        System.out.println(i.toJSON().toJSONString());
//        System.out.println(i.toString());

//        User u = new User("Marci", "Jelszó");
//        System.out.println("Encrypted: " + u.getPassword());
//        System.out.println("Decrypted: " + u.getDecryptedPassword());
//    }
}
