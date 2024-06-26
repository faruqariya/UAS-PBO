package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Faruq
 */
public class User {
    private String nama;
    private String username;
    private String password;
    
    public User(String nama, String username, String password) {
        this.nama = nama;
        this.username = username;
        this.password = password;
    }
    
    public String getNama() {
        return nama;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Metode untuk menyimpan user ke database
    public boolean registerUser() {
        String query = "INSERT INTO user (nama, username, password) VALUES (?, ?, ?)";
        try (java.sql.Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, nama);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        }
    }

    // Metode untuk memeriksa login user dari database
    public static boolean validateLogin(String username, String password) {
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try (java.sql.Connection conn = Database.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // true jika data ditemukan, false jika tidak
        } catch (SQLException ex) {
            System.err.println(ex);
            return false;
        }
    }
}
