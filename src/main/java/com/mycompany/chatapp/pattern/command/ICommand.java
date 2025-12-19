package com.mycompany.chatapp.pattern.command;

public interface ICommand {
    
    void execute();
    void undo(); 
}