package db.jdbcrepository;

import db.DatabaseConnection;
import db.entities.Doctor;
import db.repositories.DoctorRepository;
import db.utils.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcDoctorRepository implements DoctorRepository {
    @Override
    public Result<Boolean> save(Doctor doctor) {
        try {
            String sql = "INSERT INTO doctors (name, specialization) VALUES (?, ?)";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, doctor.getName());
                stmt.setString(2, doctor.getSpecialization());
                int rows = stmt.executeUpdate();
                return new Result<>(rows > 0);
            }
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        return null;
    }

    @Override
    public Result<Doctor> findById(int id) {
        try {
            String sql = "SELECT * FROM doctors WHERE id = ?";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        Doctor doctor = new Doctor(rs.getInt("id"), rs.getString("name"), rs.getString("specialization"));
                        return new Result<>(doctor);
                    }
                }
            }
            return new Result<>("Doctor not found");
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Doctor>> getAll() {
        try {
            List<Doctor> doctors = new ArrayList<>();
            String sql = "SELECT * FROM doctors";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    doctors.add(new Doctor(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("specialization")
                    ));
                }
            }
            return new Result<>(doctors);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Doctor>> findAll() {
        try {
            List<Doctor> doctors = new ArrayList<>();
            String sql = "SELECT * FROM doctors";
            try (Connection conn = DatabaseConnection.getInstance().getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    doctors.add(new Doctor(rs.getInt("id"), rs.getString("name"), rs.getString("specialization")));
                }
            }
            return new Result<>(doctors);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }
}