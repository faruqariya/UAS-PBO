package controller;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import view.Login;
import view.Main;
import view.Register;
import model.User;

/**
 *
 * @author Faruq
 */
public class RegisterController {
    private final Register registerView;

    public RegisterController(Register registerView) {
        this.registerView = registerView;
    }

    public void goToLogin() {
        JScrollPane contentScrollPane = registerView.getContentScrollPane();
        Main mainFrame = registerView.getMainFrame();
        contentScrollPane.setViewportView(new Login(contentScrollPane, mainFrame));
    }

    public boolean registerUser(String nama, String username, String password, String retypePassword) {
        if (validateRegister(nama, username, password, retypePassword)) {
            // Buat objek User dengan data dari inputan
            User newUser = new User(nama, username, password);

            // Panggil metode registerUser dari objek User
            if (newUser.registerUser()) {
                registerView.showMessage("Registrasi berhasil! Silakan login.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                goToLogin();
                return true;
            } else {
                registerView.showMessage("Registrasi gagal! Silakan coba lagi.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }

    private boolean validateRegister(String nama, String username, String password, String retypePassword) {
        if (nama.isEmpty() || username.isEmpty() || password.isEmpty() || retypePassword.isEmpty()) {
            registerView.showMessage("Silakan isi semua kolom.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!password.equals(retypePassword)) {
            registerView.showMessage("Passwords tidak cocok.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
}
