package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() throws SQLException {
        this.connection = DriverManager.getConnection(
                "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslMode=require",
                "postgres.owlrdkwmtgnifqqnijek",
                "Zharylkasyn_0");
    }

    public static synchronized DatabaseConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        return connection;
    }
}

