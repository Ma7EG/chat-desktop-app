package com.mycompany.chatapp;

import javax.swing.*;
import java.awt.*;

/**
 * Profile screen for viewing and editing user information.
 */
public class Profile extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger
            .getLogger(Profile.class.getName());

    // UI Components
    private JLabel lblAvatar;
    private JTextField txtDisplayName;
    private JTextField txtEmail;
    private JComboBox<String> cmbStatus;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnChangeAvatar;

    public Profile() {
        initComponents();
        ResponsiveHelper.configureWindow(this,
                new Dimension(500, 400),
                new Dimension(400, 350),
                false);
    }

    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profile");

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(32, 40, 58));
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Top panel - Avatar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topPanel.setBackground(new Color(32, 40, 58));

        lblAvatar = new JLabel();
        lblAvatar.setPreferredSize(new Dimension(100, 100));
        lblAvatar.setBackground(new Color(65, 143, 174));
        lblAvatar.setOpaque(true);
        lblAvatar.setHorizontalAlignment(SwingConstants.CENTER);
        lblAvatar.setText("Avatar");
        lblAvatar.setForeground(Color.WHITE);
        lblAvatar.setBorder(BorderFactory.createLineBorder(new Color(108, 225, 248), 3));

        btnChangeAvatar = new JButton("Change Photo");
        btnChangeAvatar.setBackground(new Color(34, 51, 69));
        btnChangeAvatar.setForeground(new Color(108, 225, 248));
        btnChangeAvatar.setBorder(null);
        btnChangeAvatar.setFocusPainted(false);
        btnChangeAvatar.addActionListener(e -> changeAvatar());

        JPanel avatarPanel = new JPanel(new BorderLayout(0, 10));
        avatarPanel.setBackground(new Color(32, 40, 58));
        avatarPanel.add(lblAvatar, BorderLayout.CENTER);
        avatarPanel.add(btnChangeAvatar, BorderLayout.SOUTH);
        topPanel.add(avatarPanel);

        // Center panel - Form fields
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 15));
        formPanel.setBackground(new Color(32, 40, 58));

        JLabel lblNameLabel = new JLabel("Display Name:");
        lblNameLabel.setForeground(Color.WHITE);
        lblNameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtDisplayName = new JTextField();
        txtDisplayName.setBackground(new Color(27, 33, 48));
        txtDisplayName.setForeground(Color.WHITE);
        txtDisplayName.setCaretColor(Color.WHITE);
        txtDisplayName.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(102, 102, 102)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JLabel lblEmailLabel = new JLabel("Email:");
        lblEmailLabel.setForeground(Color.WHITE);
        lblEmailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtEmail = new JTextField();
        txtEmail.setBackground(new Color(27, 33, 48));
        txtEmail.setForeground(new Color(153, 153, 153));
        txtEmail.setEditable(false);
        txtEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(102, 102, 102)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JLabel lblStatusLabel = new JLabel("Status:");
        lblStatusLabel.setForeground(Color.WHITE);
        lblStatusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        cmbStatus = new JComboBox<>(new String[] { "Online", "Offline", "Away" });
        cmbStatus.setBackground(new Color(27, 33, 48));
        cmbStatus.setForeground(Color.WHITE);

        formPanel.add(lblNameLabel);
        formPanel.add(txtDisplayName);
        formPanel.add(lblEmailLabel);
        formPanel.add(txtEmail);
        formPanel.add(lblStatusLabel);
        formPanel.add(cmbStatus);

        // Bottom panel - Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(32, 40, 58));

        btnBack = new JButton("Back");
        btnBack.setBackground(new Color(34, 51, 69));
        btnBack.setForeground(new Color(108, 225, 248));
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnBack.setPreferredSize(new Dimension(120, 35));
        btnBack.setBorder(null);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> goBack());

        btnSave = new JButton("Save Changes");
        btnSave.setBackground(new Color(65, 143, 174));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setPreferredSize(new Dimension(120, 35));
        btnSave.setBorder(null);
        btnSave.setFocusPainted(false);
        btnSave.addActionListener(e -> saveChanges());

        buttonPanel.add(btnBack);
        buttonPanel.add(btnSave);

        // Add to main panel
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(mainPanel);
        pack();
        setLocationRelativeTo(null);
    }

    private void changeAvatar() {
        FileDialog fileDialog = new FileDialog(this, "Select Avatar", FileDialog.LOAD);
        fileDialog.setFilenameFilter((dir, name) -> {
            String lowerName = name.toLowerCase();
            return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") ||
                    lowerName.endsWith(".png") || lowerName.endsWith(".gif");
        });
        fileDialog.setVisible(true);

        if (fileDialog.getFile() != null) {
            // TODO: Load and display selected image
            System.out.println("Selected: " + fileDialog.getDirectory() + fileDialog.getFile());
        }
    }

    private void saveChanges() {
        String displayName = txtDisplayName.getText();
        String status = (String) cmbStatus.getSelectedItem();

        // TODO: Save to database using Proxy
        System.out.println("Saving: " + displayName + ", Status: " + status);

        JOptionPane.showMessageDialog(this,
                "Profile updated successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void goBack() {
        this.dispose();
        new Chats().setVisible(true);
    }

    // Setters for loading user data
    public void setDisplayName(String name) {
        txtDisplayName.setText(name);
    }

    public void setEmail(String email) {
        txtEmail.setText(email);
    }

    public void setStatus(String status) {
        cmbStatus.setSelectedItem(status);
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new Profile().setVisible(true));
    }
}
