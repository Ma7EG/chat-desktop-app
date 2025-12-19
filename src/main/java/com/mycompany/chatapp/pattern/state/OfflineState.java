package com.mycompany.chatapp.pattern.state;

import com.mycompany.chatapp.model.Message;
import com.mycompany.chatapp.model.User;

public class OfflineState implements IUserState{

  @Override
    public void handleIncomingMessage(User user, Message message) {
        message.setRead(false);
        message.save();

        System.out.println("User OFFLINE → message stored in DB");
    }

    @Override
    public String getStatusText() {
        return "offline";
    }
}