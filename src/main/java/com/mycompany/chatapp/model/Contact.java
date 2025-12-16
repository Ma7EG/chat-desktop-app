package com.mycompany.chatapp.model;

import com.mycompany.chatapp.database.DatabaseManager;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class Contact {

    private int id;
    private int userId;
    private String firstName;
    private String lastName;
    private String phone;

    // Constructors
    public Contact() {
    }

    public Contact(int userId, String firstName, String lastName, String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

   
    public String getFullName() {
        return firstName + " " + (lastName != null ? lastName : "");
    }

    public boolean save() {
        Connection conn = DatabaseManager.getInstance().getConnection();

        try {
            if (this.id == 0) {
                String sql = "INSERT INTO contacts (user_id, first_name, last_name, phone) VALUES (?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, userId);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, phone);
                ps.executeUpdate();

                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    this.id = rs.getInt(1);
                }
                return true;
            } else {
                String sql = "UPDATE contacts SET first_name=?, last_name=?, phone=? WHERE id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, phone);
                ps.setInt(4, id);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error saving contact: " + e.getMessage());
            return false;
        }
    }

  
    public boolean delete() {
        if (this.id == 0)
            return false;

        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "DELETE FROM contacts WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            return false;
        }
    }


    public static Contact find(int id) {
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM contacts WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return mapResultSetToContact(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding contact: " + e.getMessage());
        }
        return null;
    }

    
    public static List<Contact> findByUserId(int userId) {
        List<Contact> contacts = new ArrayList<>();
        Connection conn = DatabaseManager.getInstance().getConnection();
        try {
            String sql = "SELECT * FROM contacts WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error finding contacts: " + e.getMessage());
        }
        return contacts;
    }

    private static Contact mapResultSetToContact(ResultSet rs) throws SQLException {
        Contact contact = new Contact();
        contact.setId(rs.getInt("id"));
        contact.setUserId(rs.getInt("user_id"));
        contact.setFirstName(rs.getString("first_name"));
        contact.setLastName(rs.getString("last_name"));
        contact.setPhone(rs.getString("phone"));
        return contact;
    }
}
