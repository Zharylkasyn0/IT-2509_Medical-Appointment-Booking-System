package edu.aitu.oop3.db.jdbcrepository;

import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.db.entities.Patient;
import edu.aitu.oop3.db.repository.PatientRepository;

import java.sql.*;

public class JdbcPatientRepository implements PatientRepository {
    @Override
    public void save(Patient patient) throws SQLException {
        String sql = "INSERT INTO patients (name, email) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getEmail());
            stmt.executeUpdate();
        }
    }

    @Override
    public Patient findById(int id) throws SQLException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Patient(rs.getInt("id"), rs.getString("name"), rs.getString("email"));
                }
            }
        }
        return null;
    }
}