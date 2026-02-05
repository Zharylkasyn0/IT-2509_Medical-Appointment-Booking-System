package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // 1. Приватное статическое поле для хранения единственного экземпляра
    private static DatabaseConnection instance;
    private Connection connection;

    // Параметры подключения (замените на свои)
    private String url = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslMode=require";
    private String user = "postgres.owlrdkwmtgnifqqnijek";
    private String password = "Zharylkasyn_0";

    // 2. Приватный конструктор, чтобы нельзя было создать объект через 'new'
    private DatabaseConnection() throws SQLException {
        try {
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new SQLException("Connection failed: " + e.getMessage());
        }
    }

    // 3. Публичный статический метод для получения инстанса
    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}