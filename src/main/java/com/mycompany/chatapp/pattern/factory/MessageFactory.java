package com.mycompany.chatapp.pattern.factory;

import com.mycompany.chatapp.model.Message;

public class MessageFactory {

    public static Message createMessage( String type, int senderId,
            int receiverId, String data ) {

        if (type == null) {
            throw new IllegalArgumentException("Message type cannot be null");
        }

        switch (type.toUpperCase()) {
            case "TEXT":
                return new TextMessage(senderId, receiverId, data);

            case "IMAGE":
                return new ImageMessage(senderId, receiverId, data);

            case "VIDEO":
                return new VideoMessage(senderId, receiverId, data);

            default:
                throw new IllegalArgumentException("Unsupported message type: " + type);
        }
    }
}