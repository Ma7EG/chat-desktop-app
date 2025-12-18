
package com.mycompany.chatapp.pattern.factory;

import com.mycompany.chatapp.model.Message;

/**
 *
 * @author nur
 */
public class TextMessage extends Message {
    public TextMessage(int senderId, int receiverId, String content) {
        super(senderId, receiverId, content, "TEXT", null);
    }
}
