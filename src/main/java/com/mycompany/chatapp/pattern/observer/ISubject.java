
package com.mycompany.chatapp.pattern.observer;

import com.mycompany.chatapp.model.Message;


/**
 * Observer Pattern - ISubject
 */
public interface ISubject {
     void notifyMessage(Message message);
    void attach(IObserver observer);
    void detach(IObserver observer);
}
