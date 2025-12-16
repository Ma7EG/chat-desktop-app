/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.chatapp;

/**
 *
 * @author computer.house
 */
public class ChatApp {

    public static void main(String[] args) {
        // Set FlatLaf Dark theme (modern look and feel)
        try {
            com.formdev.flatlaf.FlatDarkLaf.setup();
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(ChatApp.class.getName())
                    .log(java.util.logging.Level.SEVERE, null, ex);
        }

        // Launch the Login window
        java.awt.EventQueue.invokeLater(() -> {
            // Create a JFrame to hold the Login panel
            javax.swing.JFrame frame = new javax.swing.JFrame("ChatApp - Login");
            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false); // Disable maximize

            // Add the Login panel to the frame
            Login loginPanel = new Login();
            frame.add(loginPanel);

            // Size and display the frame
            frame.pack();
            frame.setLocationRelativeTo(null); // Center on screen
            frame.setVisible(true);
        });
    }
}
