package db.jdbcrepository;

import db.DatabaseConnection;
import db.entities.Patient;
import db.repositories.PatientRepository;
import db.utils.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcPatientRepository implements PatientRepository {

    @Override
    public Result<Patient> findById(int id) {
        String sql = "SELECT * FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient.Builder()
                            .setId(rs.getInt("id"))
                            .setName(rs.getString("name"))
                            .setEmail(rs.getString("email"))
                            .setPhone(rs.getString("phone"))
                            .build();
                    return new Result<>(patient);
                } else {
                    return new Result<>("Patient not found with id: " + id);
                }
            }
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Patient>> findAll() {
        String sql = "SELECT * FROM patients";
        List<Patient> patients = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                patients.add(new Patient.Builder()
                        .setId(rs.getInt("id"))
                        .setName(rs.getString("name"))
                        .setEmail(rs.getString("email"))
                        .setPhone(rs.getString("phone"))
                        .build());
            }
            return new Result<>(patients);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<Boolean> save(Patient patient) {
        String sql = "INSERT INTO patients (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, patient.getName());
            stmt.setString(2, patient.getEmail());
            stmt.setString(3, patient.getPhone());
            int rows = stmt.executeUpdate();
            return new Result<>(rows > 0);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        String sql = "DELETE FROM patients WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return new Result<>(true);
            } else {
                return new Result<>("Patient not found with id: " + id);
            }
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }
}