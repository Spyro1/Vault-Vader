import backend.User;

import javax.swing.*;

public class Main extends JFrame {

    private JPanel headerPanel;

    public Main(){
        setTitle("Vault Vader");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000,800);
        setVisible(true);

    }

    // The program starts here
    static public void main(String[] args) {
//        new Main();
//        new MainUI();
//        new LoginUI();
//        Item i = new Item();
//        System.out.println(i.toJSON().toJSONString());
//        System.out.println(i.toString());

        User u = new User("Marci", "Jelszó");
        System.out.println(u.getPassword());
        System.out.println(u.getDecryptedPassword());
    }
}
