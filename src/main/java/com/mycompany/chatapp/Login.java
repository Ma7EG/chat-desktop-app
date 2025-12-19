package com.mycompany.chatapp;

import com.mycompany.chatapp.database.DatabaseManager;
import com.mycompany.chatapp.model.User;
import com.mycompany.chatapp.proxy.UserServiceProxy;
import com.mycompany.chatapp.session.SessionManager;
import javax.swing.JOptionPane;

public class Login extends javax.swing.JPanel {

        private final UserServiceProxy userService;

        
        public Login() {
                DatabaseManager.getInstance();
                userService = new UserServiceProxy();
                initComponents();
        }

        
        @SuppressWarnings("unchecked")
        private void initComponents() {

                jButton1 = new javax.swing.JButton();
                jTextField1 = new javax.swing.JTextField();
                jTextField2 = new javax.swing.JTextField();
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jButton2 = new javax.swing.JButton();

                setBackground(new java.awt.Color(32, 40, 58));

                jButton1.setBackground(new java.awt.Color(65, 143, 174));
                jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton1.setForeground(new java.awt.Color(255, 255, 255));
                jButton1.setText("Sign in");
                jButton1.setBorder(null);
                jButton1.addActionListener(this::jButton1ActionPerformed);

                jTextField1.setBackground(new java.awt.Color(32, 40, 58));
                jTextField1.setForeground(new java.awt.Color(153, 153, 153));
                jTextField1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Email",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
                jTextField1.addActionListener(this::jTextField1ActionPerformed);

                jTextField2.setBackground(new java.awt.Color(32, 40, 58));
                jTextField2.setForeground(new java.awt.Color(153, 153, 153));
                jTextField2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Password",
                                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                                new java.awt.Font("Segoe UI", 0, 12), new java.awt.Color(255, 255, 255))); // NOI18N
                jTextField2.addActionListener(this::jTextField2ActionPerformed);

                jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
                jLabel1.setForeground(new java.awt.Color(108, 225, 248));
                jLabel1.setText("Connect Anytime , Anywhere ");

                jLabel2.setIcon(new javax.swing.ImageIcon(
                                getClass().getResource("/com/mycompany/chatapp/images/login 1.png"))); // NOI18N

                jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
                jLabel3.setForeground(new java.awt.Color(255, 255, 255));
                jLabel3.setText("Welcome Back");

                jLabel4.setIcon(
                                new javax.swing.ImageIcon(
                                                getClass().getResource("/com/mycompany/chatapp/images/wpf_chat.png"))); // NOI18N

                jButton2.setBackground(new java.awt.Color(34, 51, 69));
                jButton2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
                jButton2.setForeground(new java.awt.Color(0, 204, 204));
                jButton2.setText("Don't have an account !? Sign up");
                jButton2.setBorder(null);
                jButton2.addActionListener(this::jButton2ActionPerformed);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(46, 46, 46)
                                                                                                .addGroup(layout
                                                                                                                .createParallelGroup(
                                                                                                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                                                                                                false)
                                                                                                                .addComponent(jTextField1)
                                                                                                                .addComponent(jTextField2,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                289,
                                                                                                                                Short.MAX_VALUE)
                                                                                                                .addComponent(jButton1,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                                Short.MAX_VALUE)))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(123, 123, 123)
                                                                                                .addComponent(jLabel3))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(177, 177, 177)
                                                                                                .addComponent(jLabel4))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                                                layout.createSequentialGroup()
                                                                                                                .addContainerGap()
                                                                                                                .addComponent(jButton2,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                                289,
                                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                                                                78,
                                                                                Short.MAX_VALUE)
                                                                .addComponent(jLabel2,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                360,
                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(71, 71, 71))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                                                layout.createSequentialGroup()
                                                                                .addGap(0, 0, Short.MAX_VALUE)
                                                                                .addComponent(jLabel1)
                                                                                .addGap(155, 155, 155)));
                layout.setVerticalGroup(
                                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(
                                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(99, 99, 99)
                                                                                                .addComponent(jLabel4)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jLabel3)
                                                                                                .addGap(33, 33, 33)
                                                                                                .addComponent(jTextField1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addGap(9, 9, 9)
                                                                                                .addComponent(jTextField2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                javax.swing.GroupLayout.DEFAULT_SIZE,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                                .addComponent(jButton1,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                36,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                                .addPreferredGap(
                                                                                                                javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                                .addComponent(jButton2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                33,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                                .addGap(28, 28, 28)
                                                                                                .addComponent(jLabel2,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE,
                                                                                                                360,
                                                                                                                javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addPreferredGap(
                                                                                javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jLabel1)
                                                                .addContainerGap(56, Short.MAX_VALUE)));
        }// </editor-fold>//GEN-END:initComponents

        private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
                String email = jTextField1.getText().trim();
                String password = jTextField2.getText().trim();
                if (email.isEmpty() || password.isEmpty()) {
                        JOptionPane.showMessageDialog(this,
                                        "Please enter both email and password",
                                        "Validation Error",
                                        JOptionPane.WARNING_MESSAGE);
                        return;
                }
                User user = userService.findByEmail(email);

                if (user != null && user.getPassword().equals(password)) {
                        SessionManager.getInstance().login(user);
                        javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
                        new Chats().setVisible(true);
                } else {
                        JOptionPane.showMessageDialog(this,
                                        "Invalid email or password",
                                        "Login Failed",
                                        JOptionPane.ERROR_MESSAGE);
                }
        }// GEN-LAST:event_jButton1ActionPerformed

        private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField1ActionPerformed
        }// GEN-LAST:event_jTextField1ActionPerformed

        private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jTextField2ActionPerformed
        }// GEN-LAST:event_jTextField2ActionPerformed

        private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
                javax.swing.SwingUtilities.getWindowAncestor(this).dispose();
                new SignUp().setVisible(true);
        }// GEN-LAST:event_jButton2ActionPerformed
        private javax.swing.JButton jButton1;
        private javax.swing.JButton jButton2;
        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JTextField jTextField1;
        private javax.swing.JTextField jTextField2;
}