package com.mycompany.chatapp;

import com.mycompany.chatapp.controller.ChatController;
import com.mycompany.chatapp.model.Contact;
import com.mycompany.chatapp.model.Message;
import com.mycompany.chatapp.model.User;
import com.mycompany.chatapp.session.SessionManager;
import java.util.List;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Image;
import javax.swing.ImageIcon;

import com.mycompany.chatapp.pattern.observer.IObserver;

public class Chats extends javax.swing.JFrame implements IObserver {

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(Chats.class.getName());
        private Contact selectedContact;
        private List<Contact> contacts;
        private ChatController chatController;

        
        public Chats() {
                chatController = ChatController.getInstance();
                initComponents();
                setResizable(false); // Disable maximize
                jLabel22.setIcon(null); // No avatar by default in header
                rebuildContactsSidebar(); // Convert static panels to dynamic list
                rebuildChatArea(); // Setup dynamic message area
                loadContacts();
                initContactPanelListeners();
                User current = SessionManager.getInstance().getCurrentUser();
                if (current != null) {
                        jLabel23.setText(current.getDisplayName());
                }
                chatController.addObserver(this);
                updateStatusUI();
                javax.swing.JButton undoBtn = new javax.swing.JButton("⟲ Undo");
                undoBtn.setBounds(580, 15, 80, 25);
                undoBtn.setBackground(new java.awt.Color(34, 51, 69));
                undoBtn.setForeground(new java.awt.Color(108, 225, 248));
                undoBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                undoBtn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 225, 248)));
                undoBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                undoBtn.setToolTipText("Undo last action (Command Pattern)");
                undoBtn.addActionListener(e -> {
                        chatController.undo();
                        if (selectedContact != null) {
                                if (selectedContact.getId() != 0)
                                        displayMessages(selectedContact.getId());
                                else {
                                        User u = User.findByPhone(selectedContact.getPhone());
                                        if (u != null)
                                                displayMessages(-u.getId());
                                }
                        }
                        loadContacts(); // Refresh sidebar for potential contact deletions/restores
                });
                jPanel2.add(undoBtn);
                resizeButtonIcon(jButton2, 20, 20);
        }

        
        private void resizeButtonIcon(javax.swing.JButton button, int width, int height) {
                javax.swing.Icon icon = button.getIcon();
                if (icon instanceof javax.swing.ImageIcon) {
                        java.awt.Image img = ((javax.swing.ImageIcon) icon).getImage();
                        java.awt.Image scaledImg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
                        button.setIcon(new javax.swing.ImageIcon(scaledImg));
                }
        }

        
        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                if (selectedContact == null) {
                        javax.swing.JOptionPane.showMessageDialog(this, "Select a contact first");
                        return;
                }

                javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Images & Videos", "jpg",
                                "png", "gif", "mp4", "avi"));

                int result = fileChooser.showOpenDialog(this);
                if (result == javax.swing.JFileChooser.APPROVE_OPTION) {
                        java.io.File file = fileChooser.getSelectedFile();
                        String path = file.getAbsolutePath();
                        String name = file.getName().toLowerCase();
                        String type = "IMAGE";
                        if (name.endsWith(".mp4") || name.endsWith(".avi")) {
                                type = "VIDEO";
                        }

                        boolean sent;
                        if (selectedContact.getId() != 0) {
                                sent = chatController.sendMessageToContact(selectedContact.getId(), "[Media Content]",
                                                type);
                        } else {
                                sent = chatController.sendMessage(selectedContact.getPhone(), "[Media Content]", type);
                        }

                        if (sent) {
                                List<Message> msgs;
                                if (selectedContact.getId() != 0) {
                                        msgs = chatController.getConversationWithContact(selectedContact.getId());
                                } else {
                                        User u = User.findByPhone(selectedContact.getPhone());
                                        if (u != null) {
                                                msgs = Message.getConversation(
                                                                SessionManager.getInstance().getCurrentUserId(),
                                                                u.getId());
                                        } else {
                                                msgs = new java.util.ArrayList<>();
                                        }
                                }

                                if (!msgs.isEmpty()) {
                                        Message last = msgs.get(msgs.size() - 1);
                                        last.setMediaPath(path);
                                        last.save();
                                }
                                if (selectedContact.getId() != 0)
                                        displayMessages(selectedContact.getId());
                                else {
                                        User u = User.findByPhone(selectedContact.getPhone());
                                        if (u != null)
                                                displayMessages(-u.getId());
                                }
                        }
                }
        }// GEN-LAST:event_jButton2ActionPerformed

        
        private void loadContacts() {
                contacts = chatController.getCurrentUserContacts();
                updateContactLabels();
        }
        private javax.swing.JPanel contactsListPanel;
        private javax.swing.JScrollPane contactsScrollPane;
        private javax.swing.JPanel messagesListPanel;
        private javax.swing.JScrollPane messagesScrollPane;

        
        private void rebuildContactsSidebar() {
                if (jPanel4 == null)
                        return;
                javax.swing.JButton btnYourContact = jButton6;
                java.awt.Color sidebarBg = new java.awt.Color(27, 33, 48);
                java.awt.Color accentColor = new java.awt.Color(108, 225, 248);
                java.awt.Font buttonFont = new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13);

                btnYourContact.setBackground(new java.awt.Color(34, 51, 69));
                btnYourContact.setForeground(accentColor);
                btnYourContact.setFont(buttonFont);
                btnYourContact.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 5, 10, 5));
                btnYourContact.setFocusPainted(false);
                btnYourContact.setContentAreaFilled(true);
                btnYourContact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jPanel4.removeAll();
                jPanel4.setLayout(new java.awt.BorderLayout());
                jPanel4.setBackground(sidebarBg);
                javax.swing.JPanel headerPanel = new javax.swing.JPanel();
                headerPanel.setLayout(new java.awt.GridBagLayout());
                headerPanel.setBackground(sidebarBg);
                headerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

                java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
                gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
                gbc.weightx = 1.0;
                gbc.insets = new java.awt.Insets(2, 2, 2, 2);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                headerPanel.add(btnYourContact, gbc);
                contactsListPanel = new javax.swing.JPanel();
                contactsListPanel.setLayout(new javax.swing.BoxLayout(contactsListPanel, javax.swing.BoxLayout.Y_AXIS));
                contactsListPanel.setBackground(new java.awt.Color(37, 51, 66));
                contactsScrollPane = new javax.swing.JScrollPane(contactsListPanel);
                contactsScrollPane.setBorder(null);
                contactsScrollPane.setHorizontalScrollBarPolicy(
                                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                contactsScrollPane.getVerticalScrollBar().setUnitIncrement(16);
                contactsScrollPane.setBackground(new java.awt.Color(37, 51, 66));
                contactsScrollPane.getViewport().setBackground(new java.awt.Color(37, 51, 66));
                jPanel4.add(headerPanel, java.awt.BorderLayout.NORTH);
                jPanel4.add(contactsScrollPane, java.awt.BorderLayout.CENTER);
                updateContactLabels();

                jPanel4.revalidate();
                jPanel4.repaint();
        }

        private void rebuildChatArea() {
                if (jPanel1 == null)
                        return;
                javax.swing.JPanel headerPanel = jPanel2;
                javax.swing.JTextField inputField = jTextField1;
                javax.swing.JButton sendBtn = jButton1;
                javax.swing.JButton attachBtn = jButton2;
                jPanel1.removeAll();
                jPanel1.setLayout(new java.awt.BorderLayout());
                messagesListPanel = new javax.swing.JPanel();
                messagesListPanel.setLayout(new javax.swing.BoxLayout(messagesListPanel, javax.swing.BoxLayout.Y_AXIS));
                messagesListPanel.setBackground(new java.awt.Color(32, 40, 58));
                messagesListPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));

                messagesScrollPane = new javax.swing.JScrollPane(messagesListPanel);
                messagesScrollPane.setBorder(null);
                messagesScrollPane.setBackground(new java.awt.Color(32, 40, 58));
                messagesScrollPane.getViewport().setBackground(new java.awt.Color(32, 40, 58));
                messagesScrollPane.setHorizontalScrollBarPolicy(
                                javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                javax.swing.JPanel inputPanel = new javax.swing.JPanel(new java.awt.BorderLayout(10, 0));
                inputPanel.setBackground(new java.awt.Color(32, 40, 58));
                inputPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

                inputPanel.add(inputField, java.awt.BorderLayout.CENTER);

                javax.swing.JPanel buttonPanel = new javax.swing.JPanel(
                                new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));
                buttonPanel.setOpaque(false);
                buttonPanel.add(attachBtn);
                buttonPanel.add(sendBtn);
                inputPanel.add(buttonPanel, java.awt.BorderLayout.EAST);
                javax.swing.JPanel chatArea = new javax.swing.JPanel(new java.awt.BorderLayout());
                javax.swing.JPanel customHeader = new javax.swing.JPanel(new java.awt.BorderLayout(15, 0));
                customHeader.setBackground(new java.awt.Color(34, 51, 69));
                customHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));
                javax.swing.JPanel leftHeader = new javax.swing.JPanel(
                                new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 0));
                leftHeader.setOpaque(false);
                jLabel22.setPreferredSize(new java.awt.Dimension(46, 46));
                leftHeader.add(jLabel22);

                jLabel23.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 15));
                jLabel23.setForeground(java.awt.Color.WHITE);
                leftHeader.add(jLabel23);

                customHeader.add(leftHeader, java.awt.BorderLayout.WEST);
                javax.swing.JPanel rightHeader = new javax.swing.JPanel(
                                new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));
                rightHeader.setOpaque(false);
                javax.swing.JButton statusToggle = new javax.swing.JButton() {
                        @Override
                        protected void paintComponent(java.awt.Graphics g) {
                                java.awt.Graphics2D g2 = (java.awt.Graphics2D) g.create();
                                g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                                java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

                                boolean online = SessionManager.getInstance().getCurrentUser().isOnline();
                                if (online)
                                        g2.setColor(new java.awt.Color(0, 200, 100)); // Green
                                else
                                        g2.setColor(new java.awt.Color(100, 100, 100)); // Gray

                                g2.fillRoundRect(0, 5, 40, 20, 20, 20);
                                g2.setColor(java.awt.Color.WHITE);
                                if (online)
                                        g2.fillOval(22, 7, 16, 16);
                                else
                                        g2.fillOval(2, 7, 16, 16);

                                g2.dispose();
                        }
                };
                statusToggle.setPreferredSize(new java.awt.Dimension(40, 30));
                statusToggle.setBorderPainted(false);
                statusToggle.setContentAreaFilled(false);
                statusToggle.setFocusPainted(false);
                statusToggle.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                statusToggle.setToolTipText("Toggle Status");

                statusToggle.addActionListener(e -> {
                        User current = SessionManager.getInstance().getCurrentUser();
                        if (current != null) {
                                if (current.isOnline())
                                        chatController.setCurrentUserOffline();
                                else
                                        chatController.setCurrentUserOnline();
                                updateStatusUI();
                                statusToggle.repaint();
                        }
                });

                javax.swing.JLabel toggleLabel = new javax.swing.JLabel("Status:");
                toggleLabel.setForeground(java.awt.Color.LIGHT_GRAY);
                toggleLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));

                rightHeader.add(toggleLabel);
                rightHeader.add(statusToggle);

                customHeader.add(rightHeader, java.awt.BorderLayout.EAST);

                chatArea.add(customHeader, java.awt.BorderLayout.NORTH);
                chatArea.add(messagesScrollPane, java.awt.BorderLayout.CENTER);
                chatArea.add(inputPanel, java.awt.BorderLayout.SOUTH);
                jPanel4.setPreferredSize(new java.awt.Dimension(300, 0));
                jPanel1.add(jPanel4, java.awt.BorderLayout.WEST);
                jPanel1.add(chatArea, java.awt.BorderLayout.CENTER);

                jPanel1.revalidate();
                jPanel1.repaint();
                inputField.addFocusListener(new java.awt.event.FocusAdapter() {
                        @Override
                        public void focusGained(java.awt.event.FocusEvent e) {
                                if (inputField.getText().equals("Enter Text ......")) {
                                        inputField.setText("");
                                        inputField.setForeground(java.awt.Color.WHITE);
                                }
                        }

                        @Override
                        public void focusLost(java.awt.event.FocusEvent e) {
                                if (inputField.getText().isEmpty()) {
                                        inputField.setText("Enter Text ......");
                                        inputField.setForeground(new java.awt.Color(153, 153, 153));
                                }
                        }
                });
        }

        
        private void updateContactLabels() {
                if (contactsListPanel == null)
                        return;
                contactsListPanel.removeAll();
                javax.swing.JLabel sectionHeader = new javax.swing.JLabel("  Recent Chats");
                sectionHeader.setForeground(new java.awt.Color(108, 225, 248));
                sectionHeader.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                sectionHeader.setAlignmentX(java.awt.Component.LEFT_ALIGNMENT);
                sectionHeader.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 25));
                contactsListPanel.add(sectionHeader);
                contactsListPanel.add(javax.swing.Box.createVerticalStrut(5));
                javax.swing.JSeparator separator = new javax.swing.JSeparator();
                separator.setForeground(new java.awt.Color(60, 80, 100));
                separator.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 1));
                contactsListPanel.add(separator);
                contactsListPanel.add(javax.swing.Box.createVerticalStrut(8));
                if (contacts != null) {
                        for (int i = 0; i < contacts.size(); i++) {
                                Contact contact = contacts.get(i);
                                javax.swing.JPanel row = createContactRow(contact, i);
                                contactsListPanel.add(row);
                                contactsListPanel.add(javax.swing.Box.createVerticalStrut(2));
                        }
                }
                User currentUser = SessionManager.getInstance().getCurrentUser();
                if (currentUser != null) {
                        List<Integer> chatters = User.findConversations(currentUser.getId());
                        for (Integer chatterId : chatters) {
                                boolean isContact = false;
                                if (contacts != null) {
                                        for (Contact c : contacts) {
                                                User contactUser = User.findByPhone(c.getPhone());
                                                if (contactUser != null && contactUser.getId() == chatterId) {
                                                        isContact = true;
                                                        break;
                                                }
                                        }
                                }

                                if (!isContact && chatterId != currentUser.getId()) {
                                        User u = User.find(chatterId);
                                        if (u != null) {
                                                Contact unsaved = new Contact();
                                                unsaved.setFirstName("Unsaved User");
                                                unsaved.setLastName("("
                                                                + (u.getPhone() != null ? u.getPhone() : u.getEmail())
                                                                + ")");
                                                unsaved.setPhone(u.getPhone());

                                                final int virtualIdx = -chatterId; // Mark as unsaved
                                                javax.swing.JPanel row = createUnsavedRow(unsaved, chatterId);
                                                contactsListPanel.add(row);
                                                contactsListPanel.add(javax.swing.Box.createVerticalStrut(2));
                                        }
                                }
                        }
                }

                contactsListPanel.revalidate();
                contactsListPanel.repaint();
                if (currentUser != null) {
                }
        }

        private String getBestImagePath(Contact contact, int userId) {
                if (contact != null && contact.getImagePath() != null && !contact.getImagePath().isEmpty()) {
                        return contact.getImagePath();
                }
                if (userId > 0) {
                        User u = User.find(userId);
                        if (u != null && u.getImagePath() != null && !u.getImagePath().isEmpty()) {
                                return u.getImagePath();
                        }
                } else if (contact != null && contact.getPhone() != null) {
                        User u = User.findByPhone(contact.getPhone());
                        if (u != null && u.getImagePath() != null && !u.getImagePath().isEmpty()) {
                                return u.getImagePath();
                        }
                }
                return null;
        }

        private javax.swing.JPanel createUnsavedRow(Contact contact, int userId) {
                javax.swing.JPanel row = new javax.swing.JPanel();
                row.setLayout(null);
                row.setPreferredSize(new java.awt.Dimension(300, 58));
                row.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 58));
                row.setBackground(new java.awt.Color(45, 55, 75));
                row.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

                javax.swing.JLabel avatar = new javax.swing.JLabel(
                                getCircularImageIcon(getBestImagePath(null, userId), 42));
                avatar.setBounds(8, 8, 42, 42);
                row.add(avatar);
                javax.swing.JLabel statusDot = new javax.swing.JLabel("●");
                statusDot.setBounds(58, 10, 10, 20);
                User chatter = User.find(userId);
                if (chatter != null && chatter.isOnline()) {
                        statusDot.setForeground(new java.awt.Color(0, 255, 127)); // Green
                } else {
                        statusDot.setForeground(new java.awt.Color(255, 50, 50)); // Red
                }
                row.add(statusDot);

                javax.swing.JLabel name = new javax.swing.JLabel(contact.getFirstName());
                name.setBounds(70, 10, 150, 20);
                name.setForeground(java.awt.Color.WHITE);
                name.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
                row.add(name);

                javax.swing.JLabel phone = new javax.swing.JLabel(contact.getLastName());
                phone.setBounds(58, 30, 150, 16);
                phone.setForeground(new java.awt.Color(180, 190, 200));
                phone.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));
                row.add(phone);
                javax.swing.JLabel decorativeIcon = new javax.swing.JLabel();
                decorativeIcon.setBounds(230, 0, 70, 58);
                try {
                        java.net.URL imgUrl = getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png");
                        if (imgUrl != null) {
                                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgUrl);
                                java.awt.Image img = icon.getImage();
                                java.awt.Image scaled = img.getScaledInstance(70, 58, java.awt.Image.SCALE_SMOOTH);
                                decorativeIcon.setIcon(new javax.swing.ImageIcon(scaled));
                        }
                } catch (Exception e) {
                }
                row.add(decorativeIcon);

                row.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectedContact = new Contact();
                                selectedContact.setFirstName("Unsaved User");
                                User u = User.find(userId);
                                selectedContact.setPhone(u.getPhone());
                                displayMessages(-userId);
                                jLabel23.setText("Chat with " + contact.getFirstName());
                                jLabel22.setIcon(getCircularImageIcon(getBestImagePath(null, userId), 46));
                        }
                });
                return row;
        }

        
        private javax.swing.JPanel createContactRow(Contact contact, int index) {
                javax.swing.JPanel row = new javax.swing.JPanel();
                row.setLayout(null);
                row.setPreferredSize(new java.awt.Dimension(300, 58));
                row.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 58));
                row.setMinimumSize(new java.awt.Dimension(300, 58));
                row.setBackground(new java.awt.Color(62, 88, 121));
                row.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                javax.swing.JLabel avatar = new javax.swing.JLabel();
                avatar.setBounds(8, 8, 42, 42);

                String bestPath = getBestImagePath(contact, 0);
                avatar.setIcon(getCircularImageIcon(bestPath, 42));
                row.add(avatar);
                javax.swing.JLabel statusDot = new javax.swing.JLabel("●");
                statusDot.setBounds(58, 10, 10, 20);
                User contactUser = User.findByPhone(contact.getPhone());
                if (contactUser != null && contactUser.isOnline()) {
                        statusDot.setForeground(new java.awt.Color(0, 255, 127)); // Green
                } else {
                        statusDot.setForeground(new java.awt.Color(255, 50, 50)); // Red
                }
                row.add(statusDot);
                javax.swing.JLabel name = new javax.swing.JLabel(contact.getFullName());
                name.setBounds(70, 10, 150, 20);
                name.setForeground(java.awt.Color.WHITE);
                name.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));
                row.add(name);
                javax.swing.JLabel preview = new javax.swing.JLabel("Click to chat");
                preview.setBounds(58, 30, 150, 16);
                preview.setForeground(new java.awt.Color(150, 160, 170));
                preview.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 11));
                row.add(preview);
                javax.swing.JLabel decorativeIcon = new javax.swing.JLabel();
                decorativeIcon.setBounds(230, 0, 70, 58);
                try {
                        java.net.URL imgUrl = getClass().getResource("/com/mycompany/chatapp/images/Frame 8.png");
                        if (imgUrl != null) {
                                javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgUrl);
                                java.awt.Image img = icon.getImage();
                                java.awt.Image scaled = img.getScaledInstance(70, 58, java.awt.Image.SCALE_SMOOTH);
                                decorativeIcon.setIcon(new javax.swing.ImageIcon(scaled));
                        }
                } catch (Exception e) {
                }
                row.add(decorativeIcon);
                final int idx = index;
                row.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectContact(idx);
                        }

                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent e) {
                                row.setBackground(new java.awt.Color(72, 100, 135));
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent e) {
                                row.setBackground(new java.awt.Color(62, 88, 121));
                        }
                });

                return row;
        }

        
        private void selectContact(int index) {
                if (contacts != null && index < contacts.size()) {
                        selectedContact = contacts.get(index);
                        jLabel23.setText("Chat with " + selectedContact.getFullName());
                        String bestPath = getBestImagePath(selectedContact, 0);
                        javax.swing.ImageIcon headerIcon = getCircularImageIcon(bestPath, 46);
                        if (headerIcon != null) {
                                jLabel22.setIcon(headerIcon);
                        }

                        displayMessages(selectedContact.getId());
                        System.out.println("Selected contact: " + selectedContact.getFullName());
                }
        }

        private void displayMessages(int contactIdOrNegativeUserId) {
                if (messagesListPanel == null)
                        return;
                messagesListPanel.removeAll();

                int currentUserId = SessionManager.getInstance().getCurrentUserId();
                List<Message> messageList;
                int partnerId = -1;
                if (contactIdOrNegativeUserId < 0) {
                        partnerId = -contactIdOrNegativeUserId;
                        messageList = Message.getConversation(currentUserId, partnerId);
                } else {
                        messageList = chatController.getConversationWithContact(contactIdOrNegativeUserId);
                        Contact c = Contact.find(contactIdOrNegativeUserId);
                        if (c != null) {
                                User u = User.findByPhone(c.getPhone());
                                if (u != null)
                                        partnerId = u.getId();
                        }
                }
                if (partnerId != -1 && this.isActive()) {
                        Message.markAsRead(currentUserId, partnerId);
                }

                for (Message msg : messageList) {
                        messagesListPanel.add(createMessageBubble(msg));
                        messagesListPanel.add(javax.swing.Box.createVerticalStrut(10));
                }

                messagesListPanel.revalidate();
                messagesListPanel.repaint();
                javax.swing.SwingUtilities.invokeLater(() -> {
                        javax.swing.JScrollBar vertical = messagesScrollPane.getVerticalScrollBar();
                        vertical.setValue(vertical.getMaximum());
                });
        }

        private javax.swing.JPanel createMessageBubble(Message message) {
                boolean isMine = message.getSenderId() == SessionManager.getInstance().getCurrentUserId();

                javax.swing.JPanel bubbleContainer = new javax.swing.JPanel();
                bubbleContainer.setLayout(new java.awt.BorderLayout());
                bubbleContainer.setOpaque(false);
                bubbleContainer.setMaximumSize(new java.awt.Dimension(Short.MAX_VALUE, 400)); // Increased height for

                javax.swing.JPanel bubble = new javax.swing.JPanel();
                bubble.setLayout(new javax.swing.BoxLayout(bubble, javax.swing.BoxLayout.Y_AXIS));
                bubble.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

                if (isMine) {
                        bubble.setBackground(new java.awt.Color(65, 143, 174));
                        bubbleContainer.add(bubble, java.awt.BorderLayout.EAST);
                } else {
                        bubble.setBackground(new java.awt.Color(44, 55, 75));
                        bubbleContainer.add(bubble, java.awt.BorderLayout.WEST);
                }
                if ("IMAGE".equalsIgnoreCase(message.getMessageType())) {
                        String path = message.getMediaPath();
                        if (path != null && new java.io.File(path).exists()) {
                                try {
                                        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(path);
                                        java.awt.Image img = icon.getImage();
                                        int targetWidth = 250;
                                        int targetHeight = (img.getHeight(null) * targetWidth) / img.getWidth(null);
                                        if (targetHeight > 250) {
                                                targetHeight = 250;
                                                targetWidth = (img.getWidth(null) * targetHeight) / img.getHeight(null);
                                        }
                                        java.awt.Image scaled = img.getScaledInstance(targetWidth, targetHeight,
                                                        java.awt.Image.SCALE_SMOOTH);
                                        javax.swing.JLabel imgLabel = new javax.swing.JLabel(
                                                        new javax.swing.ImageIcon(scaled));
                                        imgLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                                        bubble.add(imgLabel);
                                } catch (Exception e) {
                                        bubble.add(new javax.swing.JLabel("[Error loading image]"));
                                }
                        } else {
                                javax.swing.JLabel imgLabel = new javax.swing.JLabel("[Image missing]");
                                imgLabel.setForeground(java.awt.Color.LIGHT_GRAY);
                                bubble.add(imgLabel);
                        }
                } else if ("VIDEO".equalsIgnoreCase(message.getMessageType())) {
                        javax.swing.JPanel videoPlaceholder = new javax.swing.JPanel(new java.awt.BorderLayout());
                        videoPlaceholder.setBackground(new java.awt.Color(30, 30, 30));
                        videoPlaceholder.setPreferredSize(new java.awt.Dimension(250, 150));
                        videoPlaceholder.setMaximumSize(new java.awt.Dimension(250, 150));
                        videoPlaceholder.setBorder(
                                        javax.swing.BorderFactory.createLineBorder(new java.awt.Color(100, 100, 100)));

                        javax.swing.JLabel playIcon = new javax.swing.JLabel("▶", javax.swing.SwingConstants.CENTER);
                        playIcon.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 40));
                        playIcon.setForeground(new java.awt.Color(200, 200, 200));
                        videoPlaceholder.add(playIcon, java.awt.BorderLayout.CENTER);

                        javax.swing.JLabel vidLabel = new javax.swing.JLabel("Video File",
                                        javax.swing.SwingConstants.CENTER);
                        vidLabel.setForeground(java.awt.Color.GRAY);
                        videoPlaceholder.add(vidLabel, java.awt.BorderLayout.SOUTH);

                        videoPlaceholder.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
                        bubble.add(videoPlaceholder);
                } else {
                        javax.swing.JLabel text = new javax.swing.JLabel(
                                        "<html><body style='width: 250px'>" + message.getContent() + "</body></html>");
                        text.setForeground(java.awt.Color.WHITE);
                        text.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 14));
                        bubble.add(text);
                }
                if (isMine) {
                        bubble.add(javax.swing.Box.createVerticalStrut(5));
                        javax.swing.JPanel statusPanel = new javax.swing.JPanel(
                                        new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 5, 0));
                        statusPanel.setOpaque(false);

                        javax.swing.JLabel statusDot = new javax.swing.JLabel("●");
                        statusDot.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));

                        javax.swing.JLabel statusText = new javax.swing.JLabel();
                        statusText.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 10));

                        if (message.isRead()) {
                                java.awt.Color readBlue = new java.awt.Color(108, 225, 248);
                                statusDot.setForeground(readBlue);
                                statusText.setText("seen");
                                statusText.setForeground(java.awt.Color.LIGHT_GRAY);
                        } else {
                                java.awt.Color unreadGray = new java.awt.Color(180, 180, 180);
                                statusDot.setForeground(unreadGray);
                                statusText.setText("unseen");
                                statusText.setForeground(unreadGray);
                        }

                        statusPanel.add(statusDot);
                        statusPanel.add(statusText);
                        javax.swing.JButton delBtn = new javax.swing.JButton("×");
                        delBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
                        delBtn.setForeground(new java.awt.Color(255, 100, 100));
                        delBtn.setBackground(new java.awt.Color(65, 143, 174));
                        delBtn.setBorder(null);
                        delBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                        delBtn.setToolTipText("Delete message (Command Pattern)");
                        delBtn.addActionListener(e -> {
                                if (chatController.deleteMessage(message)) {
                                        if (selectedContact.getId() != 0)
                                                displayMessages(selectedContact.getId());
                                        else {
                                                User u = User.findByPhone(selectedContact.getPhone());
                                                if (u != null)
                                                        displayMessages(-u.getId());
                                        }
                                }
                        });
                        statusPanel.add(javax.swing.Box.createHorizontalStrut(10));
                        statusPanel.add(delBtn);

                        bubble.add(statusPanel);
                }

                return bubbleContainer;
        }

        
        private void initContactPanelListeners() {
                java.awt.event.MouseAdapter clickHandler0 = new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectContact(0);
                        }
                };
                java.awt.event.MouseAdapter clickHandler1 = new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectContact(1);
                        }
                };
                java.awt.event.MouseAdapter clickHandler2 = new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectContact(2);
                        }
                };
                java.awt.event.MouseAdapter clickHandler3 = new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectContact(3);
                        }
                };
                java.awt.event.MouseAdapter clickHandler4 = new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseClicked(java.awt.event.MouseEvent e) {
                                selectContact(4);
                        }
                };

                jPanel3.addMouseListener(clickHandler0);
                jPanel5.addMouseListener(clickHandler1);
                jPanel6.addMouseListener(clickHandler2);
                jPanel7.addMouseListener(clickHandler3);
                jPanel8.addMouseListener(clickHandler4);
        }

        
        @SuppressWarnings("unchecked")
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jLabel22 = new javax.swing.JLabel();
                jLabel23 = new javax.swing.JLabel();
                jLabel24 = new javax.swing.JLabel();
                jPanel4 = new javax.swing.JPanel();
                jButton4 = new javax.swing.JButton();
                jButton5 = new javax.swing.JButton();
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
                jButton6 = new javax.swing.JButton();
                jTextField1 = new javax.swing.JTextField();
                jButton1 = new javax.swing.JButton();
                jButton2 = new javax.swing.JButton();

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
                javax.swing.JPanel actionsPanel = new javax.swing.JPanel(
                                new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 0));
                actionsPanel.setOpaque(false);

                javax.swing.JButton undoBtn = new javax.swing.JButton("Undo");
                undoBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
                undoBtn.setForeground(new java.awt.Color(200, 200, 200));
                undoBtn.setBackground(new java.awt.Color(50, 60, 80));
                undoBtn.setFocusPainted(false);
                undoBtn.addActionListener(e -> {
                        if (chatController.undo()) {
                                System.out.println("Command Undone");
                                if (selectedContact != null) {
                                        if (selectedContact.getId() != 0)
                                                displayMessages(selectedContact.getId());
                                        else {
                                                User partner = User.findByPhone(selectedContact.getPhone());
                                                if (partner != null)
                                                        displayMessages(-partner.getId());
                                        }
                                }
                        } else {
                                javax.swing.JOptionPane.showMessageDialog(this, "Nothing to undo");
                        }
                });

                javax.swing.JButton statusBtn = new javax.swing.JButton("Toggle Status");
                statusBtn.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 10));
                statusBtn.setForeground(new java.awt.Color(255, 255, 255));
                statusBtn.setBackground(new java.awt.Color(65, 143, 174));
                statusBtn.setFocusPainted(false);
                statusBtn.addActionListener(e -> {
                        User current = SessionManager.getInstance().getCurrentUser();
                        if (current != null) {
                                if (current.isOnline())
                                        chatController.setCurrentUserOffline();
                                else
                                        chatController.setCurrentUserOnline();
                                updateStatusUI();
                        }
                });

                actionsPanel.add(undoBtn);
                actionsPanel.add(statusBtn);

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
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(actionsPanel,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                220,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
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
                                                .addComponent(actionsPanel, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

                jPanel4.setBackground(new java.awt.Color(27, 33, 48));

                jButton4.setBackground(new java.awt.Color(34, 51, 69));
                jButton4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton4.setForeground(new java.awt.Color(108, 225, 248));
                jButton4.setText("Groups");
                jButton4.setBorder(null);
                jButton4.addActionListener(this::jButton4ActionPerformed);

                jButton5.setBackground(new java.awt.Color(34, 51, 69));
                jButton5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton5.setForeground(new java.awt.Color(108, 225, 248));
                jButton5.setText("Chats");
                jButton5.setBorder(null);
                jButton5.addActionListener(this::jButton5ActionPerformed);

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

                jButton6.setBackground(new java.awt.Color(34, 51, 69));
                jButton6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton6.setForeground(new java.awt.Color(108, 225, 248));
                jButton6.setText("Your Contact");
                jButton6.setBorder(null);
                jButton6.addActionListener(this::jButton6ActionPerformed);

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
                                                                                .addComponent(jButton6,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                Short.MAX_VALUE)
                                                                                .addGroup(jPanel4Layout
                                                                                                .createSequentialGroup()
                                                                                                .addComponent(jButton5,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                108,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jButton4,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                108,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(20, 20, 20)));
                jPanel4Layout.setVerticalGroup(
                                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                                .addContainerGap(12, Short.MAX_VALUE)
                                                                .addComponent(jButton6,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                31,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel4Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.BASELINE)
                                                                                .addComponent(jButton5,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                31,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jButton4,
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

                jTextField1.setBackground(new java.awt.Color(32, 40, 58));
                jTextField1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
                jTextField1.setForeground(new java.awt.Color(153, 153, 153));
                jTextField1.setText("Enter Text ......");
                jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
                jTextField1.addActionListener(this::jTextField1ActionPerformed);

                jButton1.setBackground(new java.awt.Color(65, 143, 174));
                jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton1.setForeground(new java.awt.Color(255, 255, 255));
                jButton1.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/Vector.png"))); // NOI18N
                jButton1.addActionListener(this::jButton1ActionPerformed);

                jButton2.setBackground(new java.awt.Color(65, 143, 174));
                jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton2.setForeground(new java.awt.Color(255, 255, 255));
                jButton2.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/upload_icon.png"))); // NOI18N
                jButton2.addActionListener(this::jButton2ActionPerformed);

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
                                                                                                .addComponent(jTextField1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                480,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jButton2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                33,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jButton1,
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
                                                                .addGroup(jPanel1Layout
                                                                                .createParallelGroup(
                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                false)
                                                                                .addComponent(jTextField1,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                34, Short.MAX_VALUE)
                                                                                .addComponent(jButton1,
                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                34, Short.MAX_VALUE)
                                                                                .addComponent(jButton2,
                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                0,
                                                                                                Short.MAX_VALUE))
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

        private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        }// GEN-LAST:event_jTextField1ActionPerformed

        private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton5ActionPerformed
        }// GEN-LAST:event_jButton5ActionPerformed

        private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
        }// GEN-LAST:event_jButton4ActionPerformed

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                String messageText = jTextField1.getText().trim();
                if (messageText.isEmpty() || messageText.equals("Enter Text ......")) {
                        return;
                }
                if (!SessionManager.getInstance().isLoggedIn()) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please login first",
                                        "Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                }
                if (selectedContact == null) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please select a contact first",
                                        "No Contact",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }
                boolean sent;
                if (selectedContact.getId() != 0) {
                        sent = chatController.sendMessageToContact(selectedContact.getId(), messageText, "TEXT");
                } else {
                        sent = chatController.sendMessage(selectedContact.getPhone(), messageText, "TEXT");
                }

                if (sent) {
                        jTextField1.setText("");
                        if (selectedContact.getId() != 0) {
                                displayMessages(selectedContact.getId());
                        } else {
                                User u = User.findByPhone(selectedContact.getPhone());
                                if (u != null)
                                        displayMessages(-u.getId());
                        }
                        System.out.println("Message sent to " + selectedContact.getFullName() + ": " + messageText);
                } else {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Failed to send message",
                                        "Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_jButton1ActionPerformed

        private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton6ActionPerformed
                this.dispose();
                new Contacts().setVisible(true);
        }// GEN-LAST:event_jButton6ActionPerformed

        
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
                
                java.awt.EventQueue.invokeLater(() -> new Chats().setVisible(true));
        }

        private void updateStatusUI() {
                User current = SessionManager.getInstance().getCurrentUser();
                if (current != null) {
                        if (selectedContact == null) {
                                jLabel23.setText(current.getDisplayName());
                                jLabel22.setIcon(null); // Clear header avatar
                        }
                        if (current.isOnline()) {
                                jLabel23.setForeground(new java.awt.Color(108, 225, 248)); // Theme blue or green?
                        } else {
                                jLabel23.setForeground(new java.awt.Color(204, 204, 204)); // Grey for offline
                        }
                }
                updateContactLabels();
        }
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton4;
        private javax.swing.JButton jButton5;
        private javax.swing.JButton jButton6;
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
        private javax.swing.JTextField jTextField1;

        @Override
        public void updateMessages(Message message) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                        loadContacts();
                        if (selectedContact != null) {
                                int currentUserId = SessionManager.getInstance().getCurrentUserId();
                                int partnerId = -1;
                                User partner = User.findByPhone(selectedContact.getPhone());
                                if (partner != null)
                                        partnerId = partner.getId();

                                if (partnerId != -1) {
                                        boolean involvesCurrent = (message.getSenderId() == partnerId
                                                        && message.getReceiverId() == currentUserId) ||
                                                        (message.getSenderId() == currentUserId
                                                                        && message.getReceiverId() == partnerId);

                                        if (involvesCurrent) {
                                                if (this.isActive() && message.getSenderId() == partnerId) {
                                                        Message.markAsRead(currentUserId, partnerId);
                                                }

                                                if (selectedContact.getId() != 0) {
                                                        displayMessages(selectedContact.getId());
                                                } else {
                                                        displayMessages(-partnerId);
                                                }
                                        }
                                }
                        }
                        showPopupNotification(message);
                });
        }

        private void showPopupNotification(Message message) {
                User sender = User.find(message.getSenderId());
                String senderName = (sender != null) ? sender.getDisplayName() : "Someone";
                if (message.getSenderId() == SessionManager.getInstance().getCurrentUserId()) {
                        return;
                }
                javax.swing.JWindow notification = new javax.swing.JWindow();
                notification.setLayout(new java.awt.BorderLayout());

                javax.swing.JPanel panel = new javax.swing.JPanel(new java.awt.BorderLayout(10, 10));
                panel.setBackground(new java.awt.Color(34, 52, 70));
                panel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(108, 225, 248), 2));

                javax.swing.JLabel title = new javax.swing.JLabel(" New Message from " + senderName);
                title.setForeground(new java.awt.Color(108, 225, 248));
                title.setFont(new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 12));
                panel.add(title, java.awt.BorderLayout.NORTH);

                String content = message.getContent();
                if ("IMAGE".equals(message.getMessageType()))
                        content = "[Image]";
                else if ("VIDEO".equals(message.getMessageType()))
                        content = "[Video]";

                javax.swing.JLabel msgLabel = new javax.swing.JLabel(" " + content);
                msgLabel.setForeground(java.awt.Color.WHITE);
                msgLabel.setFont(new java.awt.Font("Segoe UI", java.awt.Font.PLAIN, 12));
                panel.add(msgLabel, java.awt.BorderLayout.CENTER);

                notification.add(panel);
                notification.setSize(250, 60);
                java.awt.Dimension scrSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                notification.setLocation(scrSize.width - 260, scrSize.height - 100);

                notification.setAlwaysOnTop(true);
                notification.setVisible(true);
                new javax.swing.Timer(3000, e -> notification.dispose()).start();
        }

        private javax.swing.ImageIcon getCircularImageIcon(String path, int size) {
                try {
                        java.awt.Image srcImg = null;
                        if (path != null && !path.isEmpty() && new java.io.File(path).exists()) {
                                srcImg = javax.imageio.ImageIO.read(new java.io.File(path));
                        }

                        if (srcImg == null) {
                                java.net.URL url = getClass().getResource("/com/mycompany/chatapp/images/user.png");
                                if (url != null) {
                                        srcImg = javax.imageio.ImageIO.read(url);
                                }
                        }

                        if (srcImg == null) {
                                return null;
                        }

                        java.awt.image.BufferedImage combined = new java.awt.image.BufferedImage(size, size,
                                        java.awt.image.BufferedImage.TYPE_INT_ARGB);
                        java.awt.Graphics2D g2 = combined.createGraphics();
                        g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING,
                                        java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setClip(new java.awt.geom.Ellipse2D.Float(0, 0, size, size));
                        g2.drawImage(srcImg, 0, 0, size, size, null);
                        g2.dispose();

                        return new javax.swing.ImageIcon(combined);
                } catch (Exception e) {
                        return null;
                }
        }
}