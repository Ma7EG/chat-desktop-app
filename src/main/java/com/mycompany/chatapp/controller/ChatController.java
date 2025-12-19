package com.mycompany.chatapp.controller;

import com.mycompany.chatapp.model.Contact;
import com.mycompany.chatapp.model.Message;
import com.mycompany.chatapp.model.User;
import com.mycompany.chatapp.pattern.command.ICommand;
import com.mycompany.chatapp.pattern.command.SendMessageCommand;
import com.mycompany.chatapp.pattern.command.DeleteMessageCommand;
import com.mycompany.chatapp.pattern.factory.MessageFactory;
import com.mycompany.chatapp.pattern.observer.IObserver;
import com.mycompany.chatapp.pattern.observer.MessageNotifier;
import com.mycompany.chatapp.proxy.UserServiceProxy;
import com.mycompany.chatapp.session.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ChatController implements IObserver {

    private static ChatController instance;

    private UserServiceProxy userService;
    private MessageNotifier messageNotifier;
    private Stack<ICommand> commandHistory;

    private ChatController() {
        userService = new UserServiceProxy();
        messageNotifier = new MessageNotifier();
        commandHistory = new Stack<>();
        messageNotifier.attach(this);
    }

    public static ChatController getInstance() {
        if (instance == null) {
            instance = new ChatController();
        }
        return instance;
    }

    
    public boolean sendMessage(String receiverPhone, String content, String messageType) {
        int senderId = SessionManager.getInstance().getCurrentUserId();
        if (senderId == -1) {
            System.err.println("No user logged in");
            return false;
        }
        User receiver = User.findByPhone(receiverPhone);
        if (receiver == null) {
            System.err.println("User with phone " + receiverPhone + " not found");
            return false;
        }
        Message message = MessageFactory.createMessage(messageType, senderId, receiver.getId(), content);
        ICommand sendCommand = new SendMessageCommand(message);
        sendCommand.execute();
        commandHistory.push(sendCommand);
        messageNotifier.notifyMessage(message);
        receiver.receiveMessage(message);

        System.out.println("Message sent to " + receiver.getDisplayName() + ": " + content);
        return true;
    }

    
    public boolean sendMessageToContact(int contactId, String content, String messageType) {
        Contact contact = Contact.find(contactId);
        if (contact == null) {
            System.err.println("Contact not found");
            return false;
        }
        User receiver = User.findByPhone(contact.getPhone());
        if (receiver == null) {
            int senderId = SessionManager.getInstance().getCurrentUserId();
            Message message = MessageFactory.createMessage(messageType, senderId, contactId, content);

            ICommand sendCommand = new SendMessageCommand(message);
            sendCommand.execute();
            commandHistory.push(sendCommand);

            messageNotifier.notifyMessage(message);
            return true;
        }

        return sendMessage(contact.getPhone(), content, messageType);
    }

    
    public boolean deleteMessage(Message message) {
        ICommand deleteCommand = new DeleteMessageCommand(message);
        deleteCommand.execute();
        commandHistory.push(deleteCommand);
        return true;
    }

    
    public boolean undo() {
        if (!commandHistory.isEmpty()) {
            ICommand lastCommand = commandHistory.pop();
            lastCommand.undo();
            return true;
        }
        return false;
    }

    
    public List<Message> getConversationByPhone(String phone) {
        int currentUserId = SessionManager.getInstance().getCurrentUserId();
        if (currentUserId == -1)
            return new ArrayList<>();

        User otherUser = User.findByPhone(phone);
        if (otherUser == null)
            return new ArrayList<>();

        return Message.getConversation(currentUserId, otherUser.getId());
    }

    
    public List<Message> getConversationWithContact(int contactId) {
        int currentUserId = SessionManager.getInstance().getCurrentUserId();
        if (currentUserId == -1)
            return new ArrayList<>();

        Contact contact = Contact.find(contactId);
        if (contact == null)
            return new ArrayList<>();
        User otherUser = User.findByPhone(contact.getPhone());
        if (otherUser != null) {
            return Message.getConversation(currentUserId, otherUser.getId());
        }
        return Message.getConversation(currentUserId, contactId);
    }

    
    public List<Contact> getCurrentUserContacts() {
        int userId = SessionManager.getInstance().getCurrentUserId();
        if (userId == -1)
            return new ArrayList<>();
        return Contact.findByUserId(userId);
    }

    
    public void addObserver(IObserver observer) {
        messageNotifier.attach(observer);
    }

    
    public void removeObserver(IObserver observer) {
        messageNotifier.detach(observer);
    }

    @Override
    public void updateMessages(Message message) {
        System.out.println("ChatController log: New message received " + message.getContent());
    }

    
    public void setCurrentUserOnline() {
        User user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.goOnline();
        }
    }

    
    public void setCurrentUserOffline() {
        User user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            user.goOffline();
        }
    }

    
    public boolean isUserOnline(String phone) {
        User user = User.findByPhone(phone);
        if (user != null) {
            return user.isOnline();
        }
        return false;
    }
}