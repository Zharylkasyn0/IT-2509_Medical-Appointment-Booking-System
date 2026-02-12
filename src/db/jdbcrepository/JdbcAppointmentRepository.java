package db.jdbcrepository;

import db.DatabaseConnection;
import db.entities.Appointment;
import db.repositories.AppointmentRepository;
import db.utils.Result;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcAppointmentRepository implements AppointmentRepository {

    @Override
    public Result<Appointment> findById(int id) {
        String sql = "SELECT * FROM appointments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Result<>(mapResultSetToAppointment(rs));
                } else {
                    return new Result<>("Appointment not found with id: " + id);
                }
            }
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Appointment>> findAll() {
        String sql = "SELECT * FROM appointments";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToAppointment(rs));
            }
            return new Result<>(list);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<Boolean> save(Appointment appointment) {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_time, status, type) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getDoctorId());
            stmt.setInt(2, appointment.getPatientId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentTime()));
            stmt.setString(4, appointment.getStatus());
            stmt.setString(5, appointment.getType());
            int rows = stmt.executeUpdate();
            return new Result<>(rows > 0);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<Boolean> delete(int id) {
        String sql = "DELETE FROM appointments WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return new Result<>(true);
            } else {
                return new Result<>("Appointment not found with id: " + id);
            }
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Appointment>> findByDoctorId(int doctorId) {
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? AND status != 'CANCELLED'";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToAppointment(rs));
                }
            }
            return new Result<>(list);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<List<Appointment>> findByPatientId(int patientId) {
        String sql = "SELECT * FROM appointments WHERE patient_id = ? AND status != 'CANCELLED'";
        List<Appointment> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToAppointment(rs));
                }
            }
            return new Result<>(list);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    @Override
    public Result<Boolean> updateStatus(int appointmentId, String status) {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                return new Result<>(true);
            } else {
                return new Result<>("Appointment not found with id: " + appointmentId);
            }
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        return new Appointment.AppointmentBuilder()
                .setId(rs.getInt("id"))
                .setDoctorId(rs.getInt("doctor_id"))
                .setPatientId(rs.getInt("patient_id"))
                .setAppointmentTime(rs.getTimestamp("appointment_time").toLocalDateTime())
                .setStatus(rs.getString("status"))
                .setType(rs.getString("type"))
                .build();
    }
}