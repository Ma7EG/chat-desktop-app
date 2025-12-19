package com.mycompany.chatapp.pattern.observer;

import com.mycompany.chatapp.model.Message;

public interface IObserver {
 void updateMessages(Message message);
}