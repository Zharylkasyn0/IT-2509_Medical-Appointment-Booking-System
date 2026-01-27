package edu.aitu.oop3.db.repository;
import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.exeption.AppointmentException;

import java.sql.SQLException;
import java.util.List;

public interface AppointmentRepository {
    void save(Appointment appointment) throws SQLException;
    void updateStatus(int appointmentId, String status) throws SQLException,AppointmentException ;
    List<Appointment> findByDoctorId(int doctorId) throws SQLException ;
    List<Appointment> findByPatientId(int patientId) throws SQLException;
}