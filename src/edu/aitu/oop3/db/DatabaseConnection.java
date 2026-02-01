package edu.aitu.oop3.db;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.owlrdkwmtgnifqqnijek";
    private static final String PASSWORD = "Zharylkasyn_0";


    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);//bcvhcc
    }

    private static DatabaseConnection instance;

    private DatabaseConnection() {
        // Инициализация подключения
        System.out.println("Подключение к базе данных установлено.");
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void executeQuery(String query) {
        System.out.println("Выполнение запроса: " + query);
    }
}

