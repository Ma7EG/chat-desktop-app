package com.mycompany.chatapp.pattern.command;

import com.mycompany.chatapp.model.Message;

public class DeleteMessageCommand implements ICommand{

    private Message message;
    
    public DeleteMessageCommand(Message message){
        this.message = message;
    }
    
    
    public void execute(){
        message.delete();
    }
    
    public void undo(){
        message.save();
    }
}


