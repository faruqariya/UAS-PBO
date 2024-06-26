package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Faruq
 */
public class Database {
    private static Database instance;
    private Connection connection;

    private Database() {
        connect();
    }

    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        } else {
            try {
                if (instance.getConnection() == null || instance.getConnection().isClosed()) {
                    instance.connect();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    private void connect() {
        try {
            String url = "jdbc:sqlite:C:\\Users\\asus\\Documents\\1 - Dok - Back Up\\Faruq\\STIS\\4 - Pemrograman Berbasis Obyek\\Pulastis\\src\\Asset\\pulastis";
            connection = DriverManager.getConnection(url);
            System.out.println("Koneksi ke database berhasil");
        } catch (SQLException e) {
            System.err.println("Koneksi ke database gagal: " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Koneksi database ditutup");
            }
        } catch (SQLException e) {
            System.err.println("Gagal menutup koneksi database: " + e.getMessage());
        }
    }
}
