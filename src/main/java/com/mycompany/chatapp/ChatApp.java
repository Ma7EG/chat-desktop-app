package com.mycompany.chatapp;

import javax.swing.JFrame;
import com.mycompany.chatapp.database.DatabaseManager;

public class ChatApp {

    public static void main(String[] args) {

        // Initialize database connection (creates tables if not exist)
        System.out.println("Starting Chat Application...");
        DatabaseManager.getInstance();

        JFrame frame = new JFrame("Chat App");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new Login());

        // Configure responsive window settings (resizable = false to disable maximize)
        ResponsiveHelper.configureWindow(frame,
                ResponsiveHelper.LOGIN_SIZE,
                ResponsiveHelper.LOGIN_MIN_SIZE,
                false);

        frame.pack();

        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        // Close database connection when application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DatabaseManager.getInstance().closeConnection();
        }));
    }
}
