package com.mycompany.chatapp;

public class AddContact extends javax.swing.JFrame {

        private static final java.util.logging.Logger logger = java.util.logging.Logger
                        .getLogger(AddContact.class.getName());
        private String selectedImagePath = null;
        private com.mycompany.chatapp.model.Contact existingContact = null;

        
        public AddContact() {
                initComponents();
                setResizable(false); // Disable maximize
                setSize(880, 600);
                setLocationRelativeTo(null);
                try {
                        java.net.URL url = getClass().getResource("/com/mycompany/chatapp/images/user.png");
                        if (url != null) {
                                java.awt.Image img = javax.imageio.ImageIO.read(url);
                                jLabel1.setIcon(getCircularImageIcon(null, 100));
                        }
                } catch (Exception e) {
                        logger.warning("Could not scale default image: " + e.getMessage());
                }

                initImageButton(); // Connect Add Image button
        }

        
        public AddContact(com.mycompany.chatapp.model.Contact contact) {
                this();
                this.existingContact = contact;
                jLabel2.setText("Edit Contact");
                jButton2.setText("Update");
                if (contact != null) {
                        jTextField2.setText(contact.getFirstName());
                        jTextField4.setText(contact.getLastName());
                        jTextField3.setText(contact.getPhone());
                        if (contact.getImagePath() != null && !contact.getImagePath().isEmpty()) {
                                jLabel1.setIcon(getCircularImageIcon(contact.getImagePath(), 100));
                        }
                }
        }

        
        private void initImageButton() {
                jButton1.addActionListener(e -> {
                        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
                        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                                        "Images", "jpg", "jpeg", "png", "gif"));
                        if (fileChooser.showOpenDialog(this) == javax.swing.JFileChooser.APPROVE_OPTION) {
                                selectedImagePath = fileChooser.getSelectedFile().getAbsolutePath();
                                try {
                                        jLabel1.setIcon(getCircularImageIcon(selectedImagePath, 100));
                                } catch (Exception ex) {
                                        logger.warning("Could not load image: " + ex.getMessage());
                                }
                        }
                });
        }

        
        @SuppressWarnings("unchecked")
        private void initComponents() {

                jPanel1 = new javax.swing.JPanel();
                jPanel2 = new javax.swing.JPanel();
                jTextField2 = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                jTextField3 = new javax.swing.JTextField();
                jTextField4 = new javax.swing.JTextField();
                jButton1 = new javax.swing.JButton();
                jLabel2 = new javax.swing.JLabel();
                jButton2 = new javax.swing.JButton();
                jButton3 = new javax.swing.JButton();

                setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

                jPanel1.setBackground(new java.awt.Color(32, 40, 58));

                jPanel2.setBackground(new java.awt.Color(34, 52, 70));

                jTextField2.setBackground(new java.awt.Color(34, 52, 70));
                jTextField2.setForeground(new java.awt.Color(255, 255, 255));
                jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "First Name",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
                jTextField2.addActionListener(this::jTextField2ActionPerformed);

                jLabel1.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/user.png"))); // NOI18N

                jTextField3.setBackground(new java.awt.Color(34, 52, 70));
                jTextField3.setForeground(new java.awt.Color(255, 255, 255));
                jTextField3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Phone Number",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
                jTextField3.addActionListener(this::jTextField3ActionPerformed);

                jTextField4.setBackground(new java.awt.Color(34, 52, 70));
                jTextField4.setForeground(new java.awt.Color(255, 255, 255));
                jTextField4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Last Name",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
                jTextField4.addActionListener(this::jTextField4ActionPerformed);

                jButton1.setBackground(new java.awt.Color(65, 143, 174));
                jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton1.setForeground(new java.awt.Color(255, 255, 255));
                jButton1.setText("Add image");

                jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
                jLabel2.setForeground(new java.awt.Color(255, 255, 255));
                jLabel2.setText("Add Contact");

                jButton2.setBackground(new java.awt.Color(65, 143, 174));
                jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton2.setForeground(new java.awt.Color(255, 255, 255));
                jButton2.setText("Save");
                jButton2.addActionListener(this::jButton2ActionPerformed);

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(48, 48, 48)
                                                                                                .addComponent(jButton1))
                                                                                .addGroup(jPanel2Layout
                                                                                                .createSequentialGroup()
                                                                                                .addContainerGap()
                                                                                                .addComponent(jLabel1)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                22,
                                                                                Short.MAX_VALUE)
                                                                .addGroup(jPanel2Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel2Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                false)
                                                                                                .addGroup(jPanel2Layout
                                                                                                                .createSequentialGroup()
                                                                                                                .addComponent(jLabel2)
                                                                                                                .addGap(160, 160,
                                                                                                                                160))
                                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                jPanel2Layout.createSequentialGroup()
                                                                                                                                .addGroup(jPanel2Layout
                                                                                                                                                .createParallelGroup(
                                                                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                                                                                .addComponent(jButton2)
                                                                                                                                                .addComponent(jTextField3))
                                                                                                                                .addGap(10, 10, 10)))
                                                                                .addGroup(jPanel2Layout
                                                                                                .createParallelGroup(
                                                                                                                javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                                false)
                                                                                                .addComponent(jTextField4,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                359,
                                                                                                                Short.MAX_VALUE)
                                                                                                .addComponent(jTextField2,
                                                                                                                javax.swing.GroupLayout.Alignment.LEADING)))
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)));
                jPanel2Layout.setVerticalGroup(
                                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout
                                                                .createSequentialGroup()
                                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel1,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                190,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jButton1)
                                                                .addGap(105, 105, 105))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout
                                                                .createSequentialGroup()
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jLabel2)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jTextField2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                48,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jTextField4,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                48,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(13, 13, 13)
                                                                .addComponent(jTextField3,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                48,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jButton2)
                                                                .addGap(123, 123, 123)));

                jButton3.setBackground(new java.awt.Color(65, 143, 174));
                jButton3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton3.setForeground(new java.awt.Color(255, 255, 255));
                jButton3.setIcon(
                                new javax.swing.ImageIcon(getClass()
                                                .getResource("/com/mycompany/chatapp/images/back arrow_1.png"))); // NOI18N
                jButton3.setText("Back");
                jButton3.addActionListener(this::jButton3ActionPerformed);

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGroup(jPanel1Layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(23, 23, 23)
                                                                                                .addComponent(jButton3))
                                                                                .addGroup(jPanel1Layout
                                                                                                .createSequentialGroup()
                                                                                                .addGap(113, 113, 113)
                                                                                                .addComponent(jPanel2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addContainerGap(139, Short.MAX_VALUE)));
                jPanel1Layout.setVerticalGroup(
                                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(jPanel1Layout.createSequentialGroup()
                                                                .addGap(25, 25, 25)
                                                                .addComponent(jButton3)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jPanel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                317,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addContainerGap(83, Short.MAX_VALUE)));

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

        private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        }// GEN-LAST:event_jTextField2ActionPerformed

        private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField3ActionPerformed
        }// GEN-LAST:event_jTextField3ActionPerformed

        private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField4ActionPerformed
        }// GEN-LAST:event_jTextField4ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                String firstName = jTextField2.getText().trim();
                String lastName = jTextField4.getText().trim();
                String phone = jTextField3.getText().trim();
                if (firstName.isEmpty()) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "First name is required",
                                        "Validation Error",
                                        javax.swing.JOptionPane.WARNING_MESSAGE);
                        return;
                }
                int currentUserId = com.mycompany.chatapp.session.SessionManager.getInstance().getCurrentUserId();
                if (currentUserId == -1) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "Please login first",
                                        "Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                }
                com.mycompany.chatapp.model.User existingUser = com.mycompany.chatapp.model.User.findByPhone(phone);
                if (existingUser == null) {
                        existingUser = com.mycompany.chatapp.model.User.findByEmail(phone); // Using phone field for
                }

                if (existingUser == null) {
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        "No user found with this phone/email. Only registered users can be added as contacts.",
                                        "Validation Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                        return;
                }
                com.mycompany.chatapp.model.Contact contact;
                if (existingContact != null) {
                        contact = existingContact;
                        contact.setFirstName(firstName);
                        contact.setLastName(lastName);
                        contact.setPhone(phone);
                } else {
                        contact = new com.mycompany.chatapp.model.Contact(
                                        currentUserId, firstName, lastName, phone);
                }
                if (contact.getImagePath() == null && existingUser.getEmail() != null) {
                }
                if (selectedImagePath != null && !selectedImagePath.isEmpty()) {
                        contact.setImagePath(selectedImagePath);
                }

                if (contact.save()) {
                        String successMsg = (existingContact != null) ? "Contact updated successfully!"
                                        : "Contact added successfully!";
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        successMsg,
                                        "Success",
                                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                        new Contacts().setVisible(true);
                } else {
                        String errMsg = (existingContact != null) ? "Failed to update contact."
                                        : "Failed to add contact.";
                        javax.swing.JOptionPane.showMessageDialog(this,
                                        errMsg + " Please try again.",
                                        "Error",
                                        javax.swing.JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_jButton2ActionPerformed

        private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
                this.dispose();
                new Contacts().setVisible(true);
        }// GEN-LAST:event_jButton3ActionPerformed

        
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

                
                java.awt.EventQueue.invokeLater(() -> new AddContact().setVisible(true));
        }
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JButton jButton3;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JPanel jPanel1;
        private javax.swing.JPanel jPanel2;
        private javax.swing.JTextField jTextField2;
        private javax.swing.JTextField jTextField3;
        private javax.swing.JTextField jTextField4;

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