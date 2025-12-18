
package com.mycompany.chatapp.pattern.observer;

import com.mycompany.chatapp.model.Message;
import com.mycompany.chatapp.model.User;

/**
 * Observer Pattern - ChatWindow
 */
public class ChatWindow implements IObserver{
    
    private String windowName;

    public ChatWindow(String windowName) {
        this.windowName = windowName;
    }

    
    @Override
      public void updateMessages(Message message) {
          User sender = User.find(message.getSenderId());
        System.out.println("[" + windowName + "] New message from " 
                       + sender.getDisplayName() + ": " 
                       + message.getContent());
    }
}
