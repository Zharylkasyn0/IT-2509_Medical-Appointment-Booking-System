package edu.aitu.oop3.db;
import java.sql.SQLException;
import java.util.List;

public interface AppointmentRepository {
    void save(Appointment appointment) throws SQLException;
    void updateStatus(int appointmentId, String status) throws SQLException;
    List<Appointment> findByDoctorId(int doctorId) throws SQLException;
    List<Appointment> findByPatientId(int patientId) throws SQLException;
}