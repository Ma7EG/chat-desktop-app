package com.mycompany.chatapp.pattern.state;

import com.mycompany.chatapp.model.Message;
import com.mycompany.chatapp.model.User;

public interface IUserState {
    
    void handleIncomingMessage(User user, Message message);

    
    String getStatusText();
}