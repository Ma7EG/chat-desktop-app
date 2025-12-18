package com.mycompany.chatapp.pattern.command;

import com.mycompany.chatapp.model.Message;

public class SendMessageCommand implements ICommand{
 
    private Message message;
    
    public SendMessageCommand(Message message){
        this.message = message;
    }
    
    
    public void execute(){
        message.save();
    }
    
    public void undo(){
        message.delete();
    }
}
