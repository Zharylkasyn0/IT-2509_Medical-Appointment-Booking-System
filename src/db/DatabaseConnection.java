package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static Connection connection;

    private DatabaseConnection() throws SQLException {

        String url = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslMode=require";
        String user = "postgres.owlrdkwmtgnifqqnijek";
        String password = "Zharylkasyn_0";
    this.connection = DriverManager.getConnection(url,user,password);
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

