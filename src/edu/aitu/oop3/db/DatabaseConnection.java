package edu.aitu.oop3.db;

import java.sql.Connection; // ПРОВЕРЬ ЭТОТ ИМПОРТ
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Используй данные из своего Supabase
    private static final String URL = "jdbc:postgresql://aws-1-ap-northeast-1.pooler.supabase.com:5432/postgres?sslmode=require";
    private static final String USER = "postgres.owlrdkwmtgnifqqnijek";
    private static final String PASSWORD = "Zharylkasyn_0";

    // Указываем java.sql.Connection явно, чтобы не было путаницы
    public static java.sql.Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}