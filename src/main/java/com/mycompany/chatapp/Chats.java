/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.chatapp;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.Container;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;

/**
 *
 * @author computer.house
 */
public class Chats extends javax.swing.JFrame {

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(Chats.class.getName());

        // Upload button for images/videos
        private JButton btnUpload;

        /**
         * Creates new form Chats
         */
        public Chats() {
                initComponents();
                setupUploadButton();
                // Configure responsive window settings (resizable = false to disable maximize)
                ResponsiveHelper.configureWindow(this,
                                ResponsiveHelper.CHAT_SIZE,
                                ResponsiveHelper.CHAT_MIN_SIZE,
                                false);
        }

        /**
         * Setup the upload button for images and videos.
         */
        private void setupUploadButton() {
                // Create upload button
                btnUpload = new JButton();
                btnUpload.setBackground(new Color(65, 143, 174));
                btnUpload.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btnUpload.setForeground(Color.WHITE);

                // Load upload icon image
                javax.swing.ImageIcon uploadIcon = ResponsiveHelper.loadScaledImage(
                                "/com/mycompany/chatapp/images/upload_icon.png", 20, 20);
                if (uploadIcon != null) {
                        btnUpload.setIcon(uploadIcon);
                } else {
                        btnUpload.setText("📎"); // Fallback to emoji if image not found
                }

                btnUpload.setToolTipText("Upload Image or Video");
                btnUpload.setBorder(null);
                btnUpload.setFocusPainted(false);

                // Add action listener
                btnUpload.addActionListener(e -> openFileChooser());

                // Get the parent container of btnSend
                Container parent = btnSend.getParent();

                // Add button to the same panel as btnSend
                if (parent != null) {
                        // Add the upload button
                        parent.add(btnUpload);

                        // Update the layout to include the upload button
                        if (parent.getLayout() instanceof GroupLayout) {
                                updateLayoutWithUploadButton();
                        }
                }
        }

        /**
         * Update the layout to include the upload button next to send.
         */
        private void updateLayoutWithUploadButton() {
                GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addComponent(jPanel4, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(txtMessageInput)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnUpload,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                34,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnSend,
                                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                                34,
                                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                                .addContainerGap())
                                                                                .addComponent(jPanel2,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txtMessageInput,
                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnUpload,
                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnSend,
                                                                                                GroupLayout.Alignment.TRAILING,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                GroupLayout.PREFERRED_SIZE))
                                                                .addGap(14, 14, 14))
                                                .addComponent(jPanel4, GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        }

        /**
         * Open file chooser for image/video upload using native Windows dialog.
         */
        private void openFileChooser() {
                // Use native Windows file dialog for modern look
                java.awt.FileDialog fileDialog = new java.awt.FileDialog(this, "Select Image or Video",
                                java.awt.FileDialog.LOAD);

                // Set file filter for images and videos
                fileDialog.setFilenameFilter((dir, name) -> {
                        String lowerName = name.toLowerCase();
                        return lowerName.endsWith(".jpg") || lowerName.endsWith(".jpeg") ||
                                        lowerName.endsWith(".png") || lowerName.endsWith(".gif") ||
                                        lowerName.endsWith(".mp4") || lowerName.endsWith(".avi") ||
                                        lowerName.endsWith(".mov");
                });

                fileDialog.setVisible(true);

                String directory = fileDialog.getDirectory();
                String filename = fileDialog.getFile();

                if (directory != null && filename != null) {
                        java.io.File selectedFile = new java.io.File(directory, filename);
                        // TODO: Handle the selected file (send it in chat)
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                }
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated
        // Code">//GEN-BEGIN:initComponents
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jLabel22 = new javax.swing.JLabel();
                jLabel23 = new javax.swing.JLabel();
                jLabel24 = new javax.swing.JLabel();
                jPanel4 = new javax.swing.JPanel();
                btnGroups = new javax.swing.JButton();
                btnChats = new javax.swing.JButton();
                jPanel3 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jPanel5 = new javax.swing.JPanel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
                jPanel6 = new javax.swing.JPanel();
                jLabel7 = new javax.swing.JLabel();
                jLabel8 = new javax.swing.JLabel();
                jLabel9 = new javax.swing.JLabel();
                jPanel7 = new javax.swing.JPanel();
                jLabel10 = new javax.swing.JLabel();
                jLabel11 = new javax.swing.JLabel();
                jLabel12 = new javax.swing.JLabel();
                jPanel8 = new javax.swing.JPanel();
                jLabel13 = new javax.swing.JLabel();
                jLabel14 = new javax.swing.JLabel();
                jLabel15 = new javax.swing.JLabel();
                jPanel10 = new javax.swing.JPanel();
                jLabel19 = new javax.swing.JLabel();
                jLabel20 = new javax.swing.JLabel();
                jLabel21 = new javax.swing.JLabel();
                btnContacts = new javax.swing.JButton();
                txtMessageInput = new javax.swing.JTextField();
                btnSend = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBackground(new java.awt.Color(32, 40, 58));

                jPanel2.setBackground(new java.awt.Color(27, 33, 48));
                jPanel2.setAlignmentX(0.0F);
                jPanel2.setAlignmentY(0.0F);

                jLabel22.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji7.png"))); // NOI18N

                jLabel23.setBackground(new java.awt.Color(204, 204, 204));
                jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel23.setForeground(new java.awt.Color(204, 204, 204));
                jLabel23.setText("Nour Yasser");

                jLabel24.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel22,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel23)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                361,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel24,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(17, 17, 17)
                                                                .addComponent(jLabel23)
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE))
                                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                jPanel4.setBackground(new java.awt.Color(27, 33, 48));

                btnGroups.setBackground(new java.awt.Color(34, 51, 69));
                btnGroups.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnGroups.setForeground(new java.awt.Color(108, 225, 248));
                btnGroups.setText("Groups");
                btnGroups.setBorder(null);
                btnGroups.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnGroupsActionPerformed(evt);
                        }
                });

                btnChats.setBackground(new java.awt.Color(34, 51, 69));
                btnChats.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnChats.setForeground(new java.awt.Color(108, 225, 248));
                btnChats.setText("Chats");
                btnChats.setBorder(null);
                btnChats.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnChatsActionPerformed(evt);
                        }
                });

                jPanel3.setBackground(new java.awt.Color(34, 51, 69));

                jLabel1.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/animoji.png"))); // NOI18N

                jLabel2.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                jLabel3.setBackground(new java.awt.Color(204, 204, 204));
                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(204, 204, 204));
                jLabel3.setText("Noor Hussam");

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel3)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel3Layout.setVerticalGroup(
                                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel3Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap())
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel3)
                                                                .addGap(20, 20, 20))
                                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE));

                jPanel5.setBackground(new java.awt.Color(34, 51, 69));

                jLabel4.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji-1.png"))); // NOI18N

                jLabel5.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                jLabel6.setBackground(new java.awt.Color(204, 204, 204));
                jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel6.setForeground(new java.awt.Color(204, 204, 204));
                jLabel6.setText("Mahmoud Ali");

                javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                jPanel5.setLayout(jPanel5Layout);
                jPanel5Layout.setHorizontalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel6)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel5,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel5Layout.setVerticalGroup(
                                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel4,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap())
                                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel6)
                                                                .addGap(20, 20, 20)));

                jPanel6.setBackground(new java.awt.Color(34, 51, 69));

                jLabel7.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji3.png"))); // NOI18N

                jLabel8.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                jLabel9.setBackground(new java.awt.Color(204, 204, 204));
                jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel9.setForeground(new java.awt.Color(204, 204, 204));
                jLabel9.setText("Hussein Mohamed");

                javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
                jPanel6.setLayout(jPanel6Layout);
                jPanel6Layout.setHorizontalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel7,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel9)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel8,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel6Layout.setVerticalGroup(
                                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel7,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap())
                                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel9)
                                                                .addGap(20, 20, 20)));

                jPanel7.setBackground(new java.awt.Color(34, 51, 69));

                jLabel10.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji-2.png"))); // NOI18N

                jLabel11.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                jLabel12.setBackground(new java.awt.Color(204, 204, 204));
                jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel12.setForeground(new java.awt.Color(204, 204, 204));
                jLabel12.setText("Esraa Ahmed");

                javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
                jPanel7.setLayout(jPanel7Layout);
                jPanel7Layout.setHorizontalGroup(
                                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel10,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel12)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel11,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel7Layout.setVerticalGroup(
                                jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel7Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel10,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap())
                                                .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel12)
                                                                .addGap(20, 20, 20)));

                jPanel8.setBackground(new java.awt.Color(34, 51, 69));

                jLabel13.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji2.png"))); // NOI18N

                jLabel14.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                jLabel15.setBackground(new java.awt.Color(204, 204, 204));
                jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel15.setForeground(new java.awt.Color(204, 204, 204));
                jLabel15.setText("Osama Ahmed");

                javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
                jPanel8.setLayout(jPanel8Layout);
                jPanel8Layout.setHorizontalGroup(
                                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel8Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel13,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel15)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel14,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel8Layout.setVerticalGroup(
                                jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel8Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel13,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap())
                                                .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel15)
                                                                .addGap(20, 20, 20)));

                jPanel10.setBackground(new java.awt.Color(34, 51, 69));

                jLabel19.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji6.png"))); // NOI18N

                jLabel20.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png"))); // NOI18N

                jLabel21.setBackground(new java.awt.Color(204, 204, 204));
                jLabel21.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jLabel21.setForeground(new java.awt.Color(204, 204, 204));
                jLabel21.setText("Hayam");

                javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
                jPanel10.setLayout(jPanel10Layout);
                jPanel10Layout.setHorizontalGroup(
                                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel10Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel19,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                46,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel21)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel20,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                70,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel10Layout.setVerticalGroup(
                                jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel10Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addComponent(jLabel19,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addContainerGap())
                                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel21)
                                                                .addGap(20, 20, 20)));

                btnContacts.setBackground(new java.awt.Color(34, 51, 69));
                btnContacts.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnContacts.setForeground(new java.awt.Color(108, 225, 248));
                btnContacts.setText("Your Contact");
                btnContacts.setBorder(null);
                btnContacts.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnContactsActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jPanel3,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel5,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel6,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel7,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel8,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addComponent(jPanel10,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))
                                                                .addContainerGap())
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel4Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                false)
                                                                                .addComponent(btnContacts,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel4Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(btnChats,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                108,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(btnGroups,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                108,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(20, 20, 20)));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(btnContacts,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                31,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(btnChats,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                31,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnGroups,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                31,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel5,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel7,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel8,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jPanel10,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap()));

                txtMessageInput.setBackground(new java.awt.Color(32, 40, 58));
                txtMessageInput.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                txtMessageInput.setForeground(new java.awt.Color(153, 153, 153));
                txtMessageInput.setText("Enter Text ......");
                txtMessageInput.setBorder(
                                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
                txtMessageInput.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                txtMessageInputActionPerformed(evt);
                        }
                });

                btnSend.setBackground(new java.awt.Color(65, 143, 174));
                btnSend.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                btnSend.setForeground(new java.awt.Color(255, 255, 255));
                btnSend.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/Vector.png"))); // NOI18N
                btnSend.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                btnSendActionPerformed(evt);
                        }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout
                                                                .createSequentialGroup()
                                                                .addComponent(jPanel4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(txtMessageInput)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(btnSend,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                34,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addContainerGap())
                                                                                .addComponent(jPanel2,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE))));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(txtMessageInput,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(btnSend,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                34,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGap(14, 14, 14))
                                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void txtMessageInputActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_txtMessageInputActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_txtMessageInputActionPerformed

        private void btnChatsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnChatsActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnChatsActionPerformed

        private void btnGroupsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnGroupsActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnGroupsActionPerformed

        private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnSendActionPerformed
                // TODO add your handling code here:
        }// GEN-LAST:event_btnSendActionPerformed

        private void btnContactsActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnContactsActionPerformed

                this.dispose();

                new Contacts().setVisible(true);
        }// GEN-LAST:event_btnContactsActionPerformed

        /**
         * @param args the command line arguments
         */
        public static void main(String args[]) {
                /* Set the Nimbus look and feel */
                // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code
                // (optional) ">
                /*
                 * If Nimbus (introduced in Java SE 6) is not available, stay with the default
                 * look and feel.
                 * For details see
                 * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
                 */
                try {
                        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager
                                        .getInstalledLookAndFeels()) {
                                if ("Nimbus".equals(info.getName())) {
                                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                                        break;
                                }
                        }
                } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
                        logger.log(java.util.logging.Level.SEVERE, null, ex);
                }
                // </editor-fold>

                /* Create and display the form */
                java.awt.EventQueue.invokeLater(() -> new Chats().setVisible(true));
        }

        // Variables declaration - do not modify//GEN-BEGIN:variables
        private javax.swing.JButton btnChats;
        private javax.swing.JButton btnContacts;
        private javax.swing.JButton btnGroups;
        private javax.swing.JButton btnSend;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel10;
        private javax.swing.JLabel jLabel11;
        private javax.swing.JLabel jLabel12;
        private javax.swing.JLabel jLabel13;
        private javax.swing.JLabel jLabel14;
        private javax.swing.JLabel jLabel15;
        private javax.swing.JLabel jLabel19;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel20;
        private javax.swing.JLabel jLabel21;
        private javax.swing.JLabel jLabel22;
        private javax.swing.JLabel jLabel23;
        private javax.swing.JLabel jLabel24;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JLabel jLabel9;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel4;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel6;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
        private javax.swing.JTextField txtMessageInput;
        // End of variables declaration//GEN-END:variables
}
