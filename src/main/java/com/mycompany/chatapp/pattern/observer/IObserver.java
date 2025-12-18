package com.mycompany.chatapp.pattern.observer;

import com.mycompany.chatapp.model.Message;

/**
 * Observer Pattern - IObserver
 */
public interface IObserver {
 void updateMessages(Message message);
}
