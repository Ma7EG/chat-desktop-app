package com.mycompany.chatapp.model;

import com.mycompany.chatapp.database.DatabaseManager;
import com.mycompany.chatapp.pattern.state.IUserState;
import com.mycompany.chatapp.pattern.state.OfflineState;
import com.mycompany.chatapp.pattern.state.OnlineState;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class User {

    private int id;
    private String email;
    private String password;
    private String displayName;
    private String phone;
    private String status;
    private String imagePath;
    private Timestamp createdAt;
    private IUserState currentState;
    public User() {
        this.currentState = new OfflineState();
    }

    public User(String email, String password, String displayName) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.status = "offline";
        this.currentState = new OfflineState();
    }

    public User(String email, String password, String displayName, String phone) {
        this.email = email;
        this.password = password;
        this.displayName = displayName;
        this.phone = phone;
        this.status = "offline";
        this.currentState = new OfflineState();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
        if ("online".equalsIgnoreCase(status)) {
            this.currentState = new OnlineState();
        } else {
            this.currentState = new OfflineState();
        }
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    public IUserState getCurrentState() {
        return currentState;
    }

    public void setState(IUserState state) {
        this.currentState = state;
        this.status = state.getStatusText();
    }

    public void goOnline() {
        setState(new OnlineState());
        save();
    }

    public void goOffline() {
        setState(new OfflineState());
        save();
    }

    public void receiveMessage(Message message) {
        currentState.handleIncomingMessage(this, message);
    }

    public boolean isOnline() {
        return "online".equalsIgnoreCase(status);
    }

    public boolean save() {
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            if (this.id == 0) {
                String sql = "INSERT INTO users (email, password, display_name, phone, status, image_path) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, displayName);
                ps.setString(4, phone);
                ps.setString(5, status);
                ps.setString(6, imagePath);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                return true;
            } else {
                String sql = "UPDATE users SET email=?, password=?, display_name=?, phone=?, status=?, image_path=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setString(2, password);
                ps.setString(3, displayName);
                ps.setString(4, phone);
                ps.setString(5, status);
                ps.setString(6, imagePath);
                ps.setInt(7, id);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            return false;
        }
    }

    public boolean delete() {
        if (this.id == 0)
            return false;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }

    public static User find(int id) {
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM users WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user: " + e.getMessage());
        }
        return null;
    }

    public static User findByEmail(String email) {
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
        }
        return null;
    }

    public static User findByPhone(String phone) {
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM users WHERE phone = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by phone: " + e.getMessage());
        }
        return null;
    }

    public static List<User> findAll() {
        List<User> users = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM users";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        return users;
    }

    public static List<Integer> findConversations(int userId) {
        List<Integer> userIds = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT DISTINCT sender_id FROM messages WHERE receiver_id = ? " +
                    "UNION " +
                    "SELECT DISTINCT receiver_id FROM messages WHERE sender_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userIds.add(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error finding conversations: " + e.getMessage());
        }
        return userIds;
    }

    private static User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setDisplayName(rs.getString("display_name"));
        user.setPhone(rs.getString("phone"));
        user.setStatus(rs.getString("status"));
        user.setImagePath(rs.getString("image_path"));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        return user;
    }
}