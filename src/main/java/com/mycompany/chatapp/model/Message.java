package com.mycompany.chatapp.model;

import com.mycompany.chatapp.database.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Message {

    private int id;
    private int senderId;
    private int receiverId;
    private String content;
    private String messageType;
    private String mediaPath;
    private Timestamp sentAt;
    private boolean isRead;
    public Message() {
        this.messageType = "text";
        this.isRead = false;
    }

    public Message(int senderId, int receiverId, String content, String messageType, String mediaPath) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.messageType = messageType != null ? messageType : "text";
        this.mediaPath = mediaPath;
        this.isRead = false;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getMediaPath() {
        return mediaPath;
    }

    public void setMediaPath(String mediaPath) {
        this.mediaPath = mediaPath;
    }

    public Timestamp getSentAt() {
        return sentAt;
    }

    public void setSentAt(Timestamp sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    
    public boolean save() {
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            if (this.id == 0) {
                String sql = "INSERT INTO messages (sender_id, receiver_id, content, message_type, media_path, is_read) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, senderId);
                ps.setInt(2, receiverId);
                ps.setString(3, content);
                ps.setString(4, messageType);
                ps.setString(5, mediaPath);
                ps.setBoolean(6, isRead);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                return true;
            } else {
                String sql = "UPDATE messages SET content=?, is_read=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, content);
                ps.setBoolean(2, isRead);
                ps.setInt(3, id);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saving message: " + e.getMessage());
            return false;
        }
    }

 
    public boolean delete() {
        if (this.id == 0)
            return false;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "DELETE FROM messages WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting message: " + e.getMessage());
            return false;
        }
    }

    
    public static Message find(int id) {
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM messages WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToMessage(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding message: " + e.getMessage());
        }
        return null;
    }

  
    public static List<Message> getConversation(int user1Id, int user2Id) {
        List<Message> messages = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM messages WHERE " +
                    "(sender_id = ? AND receiver_id = ?) OR " +
                    "(sender_id = ? AND receiver_id = ?) " +
                    "ORDER BY sent_at ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, user1Id);
            ps.setInt(2, user2Id);
            ps.setInt(3, user2Id);
            ps.setInt(4, user1Id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                messages.add(mapResultSetToMessage(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting conversation: " + e.getMessage());
        }
        return messages;
    }

    public static void markAsRead(int receiverId, int senderId) {
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "UPDATE messages SET is_read = TRUE WHERE receiver_id = ? AND sender_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, receiverId);
            ps.setInt(2, senderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error marking messages as read: " + e.getMessage());
        }
    }

    private static Message mapResultSetToMessage(ResultSet rs) throws SQLException {
        Message message = new Message();
        message.setId(rs.getInt("id"));
        message.setSenderId(rs.getInt("sender_id"));
        message.setReceiverId(rs.getInt("receiver_id"));
        message.setContent(rs.getString("content"));
        message.setMessageType(rs.getString("message_type"));
        message.setMediaPath(rs.getString("media_path"));
        message.setSentAt(rs.getTimestamp("sent_at"));
        message.setRead(rs.getBoolean("is_read"));
        return message;
    }
}