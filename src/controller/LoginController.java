package controller;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import model.User;
import view.Home;
import view.Main;
import view.Register;
import view.Login;

/**
 *
 * @author Faruq
 */
public class LoginController {
    private final Login loginView;

    public LoginController(Login loginView) {
        this.loginView = loginView;
    }

    public void attemptLogin(String username, String password) {
        if (validateLogin(username, password)) {
            loginView.showMessage("Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            Main mainFrame = loginView.getMainFrame();
            mainFrame.setLoggedIn(true);
            JScrollPane contentScrollPane = loginView.getContentScrollPane();
            contentScrollPane.setViewportView(new Home());
        } else {
            loginView.showMessage("Ada kesalahan pada username atau password", "Login gagal", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validateLogin(String username, String password) {
        return User.validateLogin(username, password);
    }

    public void goToRegister() {
        JScrollPane contentScrollPane = loginView.getContentScrollPane();
        Main mainFrame = loginView.getMainFrame();
        contentScrollPane.setViewportView(new Register(contentScrollPane, mainFrame));
    }
}
