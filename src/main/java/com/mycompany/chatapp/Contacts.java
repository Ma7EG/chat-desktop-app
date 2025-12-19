package com.mycompany.chatapp;

import com.mycompany.chatapp.controller.ChatController;
import com.mycompany.chatapp.model.Contact;
import com.mycompany.chatapp.session.SessionManager;
import javax.swing.JOptionPane;
import java.util.List;

public class Contacts extends javax.swing.JFrame {

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(Contacts.class.getName());
        private List<Contact> contacts;
        private ChatController chatController;

        
        public Contacts() {
                chatController = ChatController.getInstance();
                initComponents();
                setResizable(false); // Disable maximize
                loadContacts(); // Load contacts from database
                initEditButtonListeners(); // Setup edit button handlers
        }

        
        private void loadContacts() {
                contacts = chatController.getCurrentUserContacts();
                updateContactLabels();
        }

        
        private void updateContactLabels() {
                jPanel3.setVisible(false);
                jPanel5.setVisible(false);
                jPanel7.setVisible(false);
                jPanel8.setVisible(false);
                jPanel10.setVisible(false);
                jButton1.setVisible(false);
                jButton2.setVisible(false);
                jButton5.setVisible(false);
                jButton6.setVisible(false);
                jButton7.setVisible(false);
                jButton8.setVisible(false);
                jButton9.setVisible(false);
                jButton10.setVisible(false);
                jButton11.setVisible(false);
                jButton12.setVisible(false);
                buildDynamicContactPanels();
        }
        private javax.swing.JPanel dynamicContactsPanel;

        
        private void buildDynamicContactPanels() {
                if (dynamicContactsPanel == null) {
                        dynamicContactsPanel = new javax.swing.JPanel();
                        dynamicContactsPanel.setLayout(
                                        new javax.swing.BoxLayout(dynamicContactsPanel, javax.swing.BoxLayout.Y_AXIS));
                        dynamicContactsPanel.setOpaque(false);
                        jPanel1.setLayout(null);
                        dynamicContactsPanel.setBounds(10, 150, 310, 350);
                        jPanel1.add(dynamicContactsPanel);
                } else {
                        dynamicContactsPanel.removeAll();
                }

                if (contacts == null || contacts.isEmpty()) {
                        dynamicContactsPanel.revalidate();
                        dynamicContactsPanel.repaint();
                        return;
                }

                for (int i = 0; i < contacts.size() && i < 5; i++) {
                        Contact contact = contacts.get(i);
                        javax.swing.JPanel row = createContactRow(contact, i);
                        dynamicContactsPanel.add(row);
                        dynamicContactsPanel.add(javax.swing.Box.createVerticalStrut(5));
                }

                dynamicContactsPanel.revalidate();
                dynamicContactsPanel.repaint();
        }

        
        private javax.swing.JPanel createContactRow(Contact contact, int index) {
                javax.swing.JPanel row = new javax.swing.JPanel();
                row.setLayout(null);
                row.setPreferredSize(new java.awt.Dimension(300, 56));
                row.setMaximumSize(new java.awt.Dimension(300, 56));
                row.setMinimumSize(new java.awt.Dimension(300, 56));
                row.setBackground(new java.awt.Color(62, 88, 121));
                javax.swing.JLabel avatarLabel = new javax.swing.JLabel();
                avatarLabel.setBounds(5, 5, 46, 46);
                String imagePath = contact.getImagePath();
                java.awt.Image img = null;
                boolean loaded = false;

                if (imagePath != null && !imagePath.isEmpty()) {
                        try {
                                java.io.File imgFile = new java.io.File(imagePath);
                                if (imgFile.exists()) {
                                        img = javax.imageio.ImageIO.read(imgFile);
                                        loaded = true;
                                }
                        } catch (Exception e) {
                                logger.warning("Image error: " + e.getMessage());
                        }
                }

                if (!loaded) {
                        try {
                                java.net.URL url = getClass().getResource("/com/mycompany/chatapp/images/user.png");
                                if (url != null) {
                                        img = javax.imageio.ImageIO.read(url);
                                        loaded = true;
                                }
                        } catch (Exception e) {
                                logger.warning("Could not load default user.png: " + e.getMessage());
                        }
                }

                if (loaded && img != null) {
                        java.awt.Image scaled = img.getScaledInstance(46, 46, java.awt.Image.SCALE_SMOOTH);
                        avatarLabel.setIcon(new javax.swing.ImageIcon(scaled));
                }
                row.add(avatarLabel);
                javax.swing.JLabel nameLabel = new javax.swing.JLabel(contact.getFullName());
                nameLabel.setBounds(60, 15, 150, 25);
                nameLabel.setForeground(java.awt.Color.WHITE);
                row.add(nameLabel);
                javax.swing.JButton editBtn = new javax.swing.JButton();
                editBtn.setBounds(230, 5, 30, 22);
                try {
                        editBtn.setIcon(new javax.swing.ImageIcon(
                                        getClass().getResource("/com/mycompany/chatapp/images/tabler_edit.png")));
                } catch (Exception e) {
                        editBtn.setText("E");
                }
                editBtn.setBackground(new java.awt.Color(65, 143, 174));
                final int idx = index;
                editBtn.addActionListener(e -> editContact(idx));
                row.add(editBtn);
                javax.swing.JButton deleteBtn = new javax.swing.JButton();
                deleteBtn.setBounds(230, 30, 30, 22);
                try {
                        deleteBtn.setIcon(new javax.swing.ImageIcon(
                                        getClass().getResource("/com/mycompany/chatapp/images/Vector_1.png")));
                } catch (Exception e) {
                        deleteBtn.setText("D");
                }
                deleteBtn.setBackground(new java.awt.Color(220, 53, 69));
                deleteBtn.addActionListener(e -> deleteContact(idx));
                row.add(deleteBtn);

                return row;
        }

        
        private void editContact(int index) {
                if (contacts == null || index >= contacts.size()) {
                        JOptionPane.showMessageDialog(this, "No contact at this position");
                        return;
                }
                Contact contact = contacts.get(index);
                new AddContact(contact).setVisible(true);
                this.dispose();
        }

        
        private void deleteContact(int index) {
                if (contacts == null || index >= contacts.size()) {
                        JOptionPane.showMessageDialog(this, "No contact at this position");
                        return;
                }
                Contact contact = contacts.get(index);

                int confirm = JOptionPane.showConfirmDialog(this,
                                "Delete " + contact.getFullName() + "?",
                                "Confirm", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                        if (contact.delete()) {
                                JOptionPane.showMessageDialog(this, "Contact deleted!");
                                loadContacts();
                        } else {
                                JOptionPane.showMessageDialog(this, "Failed to delete", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                        }
                }
        }

        
        private void initEditButtonListeners() {
                jButton1.addActionListener(e -> editContact(0));
                jButton5.addActionListener(e -> editContact(1));
                jButton7.addActionListener(e -> editContact(2));
                jButton9.addActionListener(e -> editContact(3));
                jButton11.addActionListener(e -> editContact(4));
        }

        @SuppressWarnings("unchecked")

        private void initComponents() {

                jButton3 = new javax.swing.JButton();
                jPanel1 = new javax.swing.JPanel();
                jButton4 = new javax.swing.JButton();
                jPanel3 = new javax.swing.JPanel();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jPanel5 = new javax.swing.JPanel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();
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
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();
                jButton5 = new javax.swing.JButton();
                jButton6 = new javax.swing.JButton();
                jButton7 = new javax.swing.JButton();
                jButton8 = new javax.swing.JButton();
                jButton9 = new javax.swing.JButton();
                jButton10 = new javax.swing.JButton();
                jButton11 = new javax.swing.JButton();
                jButton12 = new javax.swing.JButton();
                jButton13 = new javax.swing.JButton();
                jLabel8 = new javax.swing.JLabel();

                jButton3.setBackground(new java.awt.Color(0, 204, 204));
                jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton3.setForeground(new java.awt.Color(255, 255, 255));
                jButton3.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/back arrow_1.png"))); // NOI18N
                jButton3.setText("Back");
                jButton3.addActionListener(this::jButton3ActionPerformed);

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBackground(new java.awt.Color(32, 40, 58));

                jButton4.setBackground(new java.awt.Color(65, 143, 174));
                jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton4.setForeground(new java.awt.Color(255, 255, 255));
                jButton4.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/back arrow_1.png"))); // NOI18N
                jButton4.setText("Back");
                jButton4.addActionListener(this::jButton4ActionPerformed);

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
                                                                                82,
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
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji5.png"))); // NOI18N

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
                                                                                83,
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

                jPanel7.setBackground(new java.awt.Color(34, 51, 69));

                jLabel10.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/animoji4.png"))); // NOI18N

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
                                                                                86,
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
                                                                                75,
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
                                                                                125,
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

                jButton1.setBackground(new java.awt.Color(65, 143, 174));
                jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton1.setForeground(new java.awt.Color(255, 255, 255));
                jButton1.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/tabler_edit.png"))); // NOI18N
                jButton1.setToolTipText("");
                jButton1.setAlignmentY(0.0F);

                jButton2.setBackground(new java.awt.Color(255, 0, 0));
                jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton2.setForeground(new java.awt.Color(255, 255, 255));
                jButton2.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/mdi_bin.png"))); // NOI18N
                jButton2.setActionCommand("Delete");
                jButton2.setAlignmentY(0.0F);
                jButton2.addActionListener(this::jButton2ActionPerformed);

                jButton5.setBackground(new java.awt.Color(65, 143, 174));
                jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton5.setForeground(new java.awt.Color(255, 255, 255));
                jButton5.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/tabler_edit.png"))); // NOI18N
                jButton5.setAlignmentY(0.0F);

                jButton6.setBackground(new java.awt.Color(255, 0, 0));
                jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton6.setForeground(new java.awt.Color(255, 255, 255));
                jButton6.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/mdi_bin.png"))); // NOI18N
                jButton6.setActionCommand("Delete");
                jButton6.setAlignmentY(0.0F);
                jButton6.addActionListener(this::jButton6ActionPerformed);

                jButton7.setBackground(new java.awt.Color(65, 143, 174));
                jButton7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton7.setForeground(new java.awt.Color(255, 255, 255));
                jButton7.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/tabler_edit.png"))); // NOI18N
                jButton7.setAlignmentY(0.0F);

                jButton8.setBackground(new java.awt.Color(255, 0, 0));
                jButton8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton8.setForeground(new java.awt.Color(255, 255, 255));
                jButton8.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/mdi_bin.png"))); // NOI18N
                jButton8.setActionCommand("Delete");
                jButton8.setAlignmentY(0.0F);
                jButton8.addActionListener(this::jButton8ActionPerformed);

                jButton9.setBackground(new java.awt.Color(65, 143, 174));
                jButton9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton9.setForeground(new java.awt.Color(255, 255, 255));
                jButton9.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/tabler_edit.png"))); // NOI18N
                jButton9.setAlignmentY(0.0F);

                jButton10.setBackground(new java.awt.Color(255, 0, 0));
                jButton10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton10.setForeground(new java.awt.Color(255, 255, 255));
                jButton10.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/mdi_bin.png"))); // NOI18N
                jButton10.setActionCommand("Delete");
                jButton10.setAlignmentY(0.0F);
                jButton10.addActionListener(this::jButton10ActionPerformed);

                jButton11.setBackground(new java.awt.Color(65, 143, 174));
                jButton11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton11.setForeground(new java.awt.Color(255, 255, 255));
                jButton11.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/tabler_edit.png"))); // NOI18N
                jButton11.setAlignmentY(0.0F);

                jButton12.setBackground(new java.awt.Color(255, 0, 0));
                jButton12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton12.setForeground(new java.awt.Color(255, 255, 255));
                jButton12.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/mdi_bin.png"))); // NOI18N
                jButton12.setActionCommand("Delete");
                jButton12.setAlignmentY(0.0F);
                jButton12.addActionListener(this::jButton12ActionPerformed);

                jButton13.setBackground(new java.awt.Color(65, 143, 174));
                jButton13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton13.setForeground(new java.awt.Color(255, 255, 255));
                jButton13.setText("Add New Contact");
                jButton13.setBorder(null);
                jButton13.addActionListener(this::jButton13ActionPerformed);

                jLabel8.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/mdi_bin2.png"))); // NOI18N

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                .addComponent(jButton4)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                                                .addComponent(jPanel5,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                                .addComponent(jPanel3,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                                .addComponent(jPanel7,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                                                                                .addComponent(jPanel8,
                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                                .addComponent(jPanel10,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                false)
                                                                                                                                                .addComponent(jButton1,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jButton2,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jButton5,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jButton6,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(jButton7,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jButton8,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(jButton9,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jButton10,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addComponent(jButton11,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                                                Short.MAX_VALUE)
                                                                                                                                                .addComponent(jButton12,
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)))))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(74, 74, 74)
                                                                                                .addComponent(jButton13,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                154,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                42,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel8,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                467,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                Short.MAX_VALUE)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addContainerGap()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                                .addGroup(jPanel1Layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addComponent(jButton11)
                                                                                                                                .addPreferredGap(
                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                .addComponent(jButton12))
                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                .createSequentialGroup()
                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                .addGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                .addGroup(jPanel1Layout
                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                .addGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                jPanel1Layout
                                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                                .addGroup(
                                                                                                                                                                                                                                                jPanel1Layout
                                                                                                                                                                                                                                                                .createParallelGroup(
                                                                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                                                                                                                                                                                                .addGroup(
                                                                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                                                                                jPanel1Layout
                                                                                                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                                                                                                                jButton1)
                                                                                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                                                                                                                jButton2)
                                                                                                                                                                                                                                                                                                .addGap(64,
                                                                                                                                                                                                                                                                                                                64,
                                                                                                                                                                                                                                                                                                                64))
                                                                                                                                                                                                                                                                .addGroup(
                                                                                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                                                                                jPanel1Layout
                                                                                                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                                                                                                                jButton5)
                                                                                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                                                                                                                jButton6)))
                                                                                                                                                                                                                                .addGap(64,
                                                                                                                                                                                                                                                64,
                                                                                                                                                                                                                                                64))
                                                                                                                                                                                                .addGroup(
                                                                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                                                                jPanel1Layout
                                                                                                                                                                                                                                .createSequentialGroup()
                                                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                                                jButton7)
                                                                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                                                                .addComponent(
                                                                                                                                                                                                                                                jButton8)))
                                                                                                                                                                                .addGap(64, 64, 64))
                                                                                                                                                .addGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                                                                jPanel1Layout.createSequentialGroup()
                                                                                                                                                                                .addComponent(jButton9)
                                                                                                                                                                                .addPreferredGap(
                                                                                                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                                                                                                .addComponent(jButton10)))
                                                                                                                                .addGap(64, 64, 64))))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jButton4)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jButton13,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                31,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(18, 18, 18)
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
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                pack();
        }// </editor-fold>//GEN-END:initComponents

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
        }// GEN-LAST:event_jButton3ActionPerformed

        private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnBackActionPerformed

                this.dispose();

                new Chats().setVisible(true);
        }// GEN-LAST:event_btnBackActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                deleteContact(0);
        }// GEN-LAST:event_jButton2ActionPerformed

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
                deleteContact(1);
        }// GEN-LAST:event_jButton6ActionPerformed

        private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton8ActionPerformed
                deleteContact(2);
        }// GEN-LAST:event_jButton8ActionPerformed

        private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton10ActionPerformed
                deleteContact(3);
        }// GEN-LAST:event_jButton10ActionPerformed

        private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton12ActionPerformed
                deleteContact(4);
        }// GEN-LAST:event_jButton12ActionPerformed

        private void btnAddContactNavActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_btnAddContactNavActionPerformed

                this.dispose();

                new AddContact().setVisible(true);
        }// GEN-LAST:event_btnAddContactNavActionPerformed

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
                this.dispose();
                new Chats().setVisible(true);
        }

        private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {
                this.dispose();
                new AddContact().setVisible(true);
        }

        
        public static void main(String args[]) {
                
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

                
                java.awt.EventQueue.invokeLater(() -> new Contacts().setVisible(true));
        }
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton10;
        private javax.swing.JButton jButton11;
        private javax.swing.JButton jButton12;
        private javax.swing.JButton jButton13;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton6;
        private javax.swing.JButton jButton7;
        private javax.swing.JButton jButton8;
        private javax.swing.JButton jButton9;
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
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel6;
        private javax.swing.JLabel jLabel8;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel10;
        private javax.swing.JPanel jPanel3;
        private javax.swing.JPanel jPanel5;
        private javax.swing.JPanel jPanel7;
        private javax.swing.JPanel jPanel8;
}