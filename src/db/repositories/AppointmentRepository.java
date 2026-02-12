package db.repositories;

import db.entities.Appointment;
import db.utils.Result;

import java.util.List;

public interface AppointmentRepository extends IRepository<Appointment> {
    Result<List<Appointment>> findByDoctorId(int doctorId);
    Result<List<Appointment>> findByPatientId(int patientId);
    Result<Boolean> updateStatus(int appointmentId, String status);
}