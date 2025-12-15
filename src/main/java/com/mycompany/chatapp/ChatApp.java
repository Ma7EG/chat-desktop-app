package com.mycompany.chatapp;

import javax.swing.JFrame;

public class ChatApp {

    public static void main(String[] args) {
       
        JFrame frame = new JFrame("Chat App");
        
       
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        frame.add(new Login());
        
       
        frame.pack();
        
       
        frame.setLocationRelativeTo(null);
        
       
        frame.setVisible(true);
    }
}