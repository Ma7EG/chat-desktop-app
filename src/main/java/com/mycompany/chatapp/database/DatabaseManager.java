package com.mycompany.chatapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    private static DatabaseManager instance;
    private Connection connection;

    private static final String DB_URL = "jdbc:sqlite:chatapp.db";
    private DatabaseManager() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Database connected successfully");
            initializeTables();
        } catch (SQLException e) {
            System.err.println("Failed to connect to database");
            e.printStackTrace();
        }
    }

    
    private void initializeTables() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users (" +
                            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "    email TEXT UNIQUE NOT NULL," +
                            "    password TEXT NOT NULL," +
                            "    display_name TEXT," +
                            "    phone TEXT UNIQUE," +
                            "    status TEXT DEFAULT 'offline'," +
                            "    image_path TEXT," +
                            "    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                            ")");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS contacts (" +
                            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "    user_id INTEGER NOT NULL," +
                            "    first_name TEXT NOT NULL," +
                            "    last_name TEXT," +
                            "    phone TEXT," +
                            "    image_path TEXT," +
                            "    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE" +
                            ")");
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS messages (" +
                            "    id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "    sender_id INTEGER NOT NULL," +
                            "    receiver_id INTEGER NOT NULL," +
                            "    content TEXT," +
                            "    message_type TEXT DEFAULT 'text'," +
                            "    media_path TEXT," +
                            "    sent_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                            "    is_read INTEGER DEFAULT 0," +
                            "    FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE," +
                            "    FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE" +
                            ")");

            System.out.println("Database tables initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException e) {
            System.err.println("Error closing database connection");
            e.printStackTrace();
        }
    }
}