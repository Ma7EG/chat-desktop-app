package com.mycompany.chatapp;

import javax.swing.JFrame;

public class ChatApp {

    public static void main(String[] args) {

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
    }
}