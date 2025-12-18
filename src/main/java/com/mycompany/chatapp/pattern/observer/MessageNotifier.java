package com.mycompany.chatapp.pattern.observer;

import com.mycompany.chatapp.model.Message;
import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern - MessageNotifier
 */
public class MessageNotifier implements ISubject  {
    private List<IObserver>observers=new ArrayList<>();

    @Override
    public void notifyMessage(Message message) {
      for(IObserver observer:observers){
          observer.updateMessages(message);
      }
    }

    @Override
    public void attach(IObserver observer) {
       observers.add(observer);
    }

    @Override
    public void detach(IObserver observer) {
        observers.remove(observer);
    }
}
