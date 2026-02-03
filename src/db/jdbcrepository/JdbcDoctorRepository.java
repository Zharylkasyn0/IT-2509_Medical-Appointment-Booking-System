package db.jdbcrepository;

import db.DatabaseConnection;
import db.entities.Doctor;
import db.repositories.DoctorRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDoctorRepository implements DoctorRepository {
    @Override
    public void save(Doctor doctor) throws SQLException {
        String sql = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, doctor.getName());
            stmt.setString(2, doctor.getSpecialization());
            stmt.executeUpdate();
        }
    }

    @Override
    public Doctor findById(int id) throws SQLException {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Doctor(rs.getInt("id"), rs.getString("name"), rs.getString("specialization"));
                }
            }
        }
        return null;
    }

    @Override
    public List<Doctor> getAll() {
        return List.of();
    }

    public boolean save(Doctor entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public List<Doctor> findAll() throws SQLException {
        List<Doctor> doctors = new ArrayList<>();
        String sql = "SELECT * FROM doctors";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                doctors.add(new Doctor(rs.getInt("id"), rs.getString("name"), rs.getString("specialization")));
            }
        }
        return doctors;
    }
}
