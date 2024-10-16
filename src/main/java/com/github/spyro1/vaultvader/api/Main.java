package com.github.spyro1.vaultvader.api;

import com.github.spyro1.vaultvader.frontend.ui.LoginUI;

import javax.swing.*;

public class Main {
    
    /**
     * The main method serves as the entry point of the application.
     * @param args Command-line arguments (not used in this application).
     */
    public static void main(String[] args) {
        try {
            new LoginUI(); // RUN LOGIN
        } catch (Exception e) {
            // Display a message dialog with the error in case of a fatal exception
            JOptionPane.showMessageDialog(null, e, "Fatal error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
