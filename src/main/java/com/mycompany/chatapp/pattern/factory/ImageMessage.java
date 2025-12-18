
package com.mycompany.chatapp.pattern.factory;

import com.mycompany.chatapp.model.Message;

/**
 *
 * @author nur
 */
public class ImageMessage extends Message {
    public ImageMessage(int senderId, int receiverId, String imagePath) {
        super(senderId, receiverId, null, "IMAGE", imagePath);
    }
}
