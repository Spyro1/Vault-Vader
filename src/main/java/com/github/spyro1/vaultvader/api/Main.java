package com.github.spyro1.vaultvader.api;

import com.github.spyro1.vaultvader.frontend.ui.LoginUI;

import javax.swing.*;

public class Main {

    static public void main(String[] args){
        try{
            new LoginUI(); // TO RUN
//            new MainUI(); // TESTING
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, e, "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
