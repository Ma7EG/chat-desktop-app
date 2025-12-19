package com.mycompany.chatapp.pattern.observer;

import com.mycompany.chatapp.model.Message;

public interface ISubject {
     void notifyMessage(Message message);
    void attach(IObserver observer);
    void detach(IObserver observer);
}