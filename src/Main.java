import backend.Item;
import frontend.GUI;

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
    static public void main(String[] args){
//        new Main();
//        new GUI();
        Item i = new Item();
        System.out.println(i.toJSON().toJSONString());
        System.out.println(i.toString());
    }
}
