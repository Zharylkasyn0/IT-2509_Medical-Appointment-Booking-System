package edu.aitu.oop3.db.jdbcrepository;
import edu.aitu.oop3.db.DatabaseConnection;
import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.exeption.AppointmentException;
import edu.aitu.oop3.db.exeption.AppointmentNotFoundException;
import edu.aitu.oop3.db.repositories.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


public class JdbcAppointmentRepository implements AppointmentRepository {

    @Override
    public List<Appointment> findByPatientId(int patientId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE patient_id = ? AND status != 'CANCELLED'";
        List<Appointment> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToAppointment(rs));
            }
        }
        return list;
    }

    @Override
    public void save(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointments (doctor_id, patient_id, appointment_time, status) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, appointment.getDoctorId());
            stmt.setInt(2, appointment.getPatientId());
            stmt.setTimestamp(3, Timestamp.valueOf(appointment.getAppointmentTime()));
            stmt.setString(4, appointment.getStatus());

            stmt.executeUpdate();
        }
    }

    @Override
    public void updateStatus(int appointmentId, String status) throws SQLException, AppointmentException {
        String sql = "UPDATE appointments SET status = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            int rowsAffected = stmt.executeUpdate();


            if (rowsAffected == 0) {

                throw new AppointmentNotFoundException(appointmentId);
            }
        }
    }


    @Override
    public List<Appointment> findByDoctorId(int doctorId) throws SQLException {
        String sql = "SELECT * FROM appointments WHERE doctor_id = ? AND status != 'CANCELLED'";
        List<Appointment> list = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(mapResultSetToAppointment(rs));
            }
        }
        return list;
    }


    private Appointment mapResultSetToAppointment(ResultSet rs) throws SQLException {
        return new Appointment(
                rs.getInt("id"),
                rs.getInt("doctor_id"),
                rs.getInt("patient_id"),
                rs.getTimestamp("appointment_time").toLocalDateTime(),
                rs.getString("status")
        );
    }
}