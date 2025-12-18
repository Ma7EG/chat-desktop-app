
package com.mycompany.chatapp.pattern.factory;

import com.mycompany.chatapp.model.Message;

/**
 *
 * @author nur
 */
public class VideoMessage extends Message {
    public VideoMessage(int senderId, int receiverId, String videoPath) {
        super(senderId, receiverId, null, "VIDEO", videoPath);
    }
}
