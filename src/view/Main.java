/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.JOptionPane;

/*
 *
 * @author Faruq
 */
public class Main extends javax.swing.JFrame {
    private boolean loggedIn = false;
    
    public Main() {
        initComponents();
        contentScrollPane.setViewportView(new Login(contentScrollPane, this));
        updateMenuItems();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentScrollPane = new javax.swing.JScrollPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        DashboradMenuItem = new javax.swing.JMenuItem();
        FormMenuItem = new javax.swing.JMenuItem();
        LoginLogoutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        contentScrollPane.setBackground(new java.awt.Color(255, 255, 255));

        jMenu1.setText("Menu");

        DashboradMenuItem.setText("Dashboard");
        DashboradMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DashboradMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(DashboradMenuItem);

        FormMenuItem.setText("Form Pinjam");
        FormMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FormMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(FormMenuItem);

        LoginLogoutMenuItem.setText("Login");
        LoginLogoutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginLogoutMenuItemActionPerformed(evt);
            }
        });
        jMenu1.add(LoginLogoutMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(contentScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 840, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(contentScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DashboradMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DashboradMenuItemActionPerformed
        contentScrollPane.setViewportView(new Home());
    }//GEN-LAST:event_DashboradMenuItemActionPerformed

    private void FormMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FormMenuItemActionPerformed
        contentScrollPane.setViewportView(new Pinjam());
    }//GEN-LAST:event_FormMenuItemActionPerformed

    private void LoginLogoutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginLogoutMenuItemActionPerformed
        if (loggedIn) {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                setLoggedIn(false);
                contentScrollPane.setViewportView(new Login(contentScrollPane, this));
            }
        } else {
            contentScrollPane.setViewportView(new Login(contentScrollPane, this));
        }
    }//GEN-LAST:event_LoginLogoutMenuItemActionPerformed

    private void updateMenuItems() {
        DashboradMenuItem.setEnabled(loggedIn);
        FormMenuItem.setEnabled(loggedIn);
        LoginLogoutMenuItem.setText(loggedIn ? "Logout" : "Login");
    }
    
    public void setLoggedIn(boolean isLoggedIn) {
        this.loggedIn = isLoggedIn;
        updateMenuItems();
    }   
    
    /** 
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        FlatLightLaf.setup();
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem DashboradMenuItem;
    private javax.swing.JMenuItem FormMenuItem;
    private javax.swing.JMenuItem LoginLogoutMenuItem;
    private javax.swing.JScrollPane contentScrollPane;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    // End of variables declaration//GEN-END:variables
}
