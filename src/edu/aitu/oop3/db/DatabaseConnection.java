package edu.aitu.oop3.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL =
            "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslmode=require";

    // ВАЖНО: Используйте ваш ID из скриншота
    private static final String USER = "postgres.owlrdkwmtgnifqqnijek";

    private static final String PASSWORD = "Zharylkasyn_0";

    private DatabaseConnection() {
        // no instances
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}