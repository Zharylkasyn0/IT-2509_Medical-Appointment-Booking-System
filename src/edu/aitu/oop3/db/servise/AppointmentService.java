package edu.aitu.oop3.db.servise;

import edu.aitu.oop3.db.entities.Appointment;
import edu.aitu.oop3.db.entities.Doctor;
import edu.aitu.oop3.db.entities.Patient;
import edu.aitu.oop3.db.exeption.AppointmentException;
import edu.aitu.oop3.db.exeption.TimeSlotOccupiedException;
import edu.aitu.oop3.db.repository.AppointmentRepository;
import edu.aitu.oop3.db.repository.DoctorRepository;
import edu.aitu.oop3.db.repository.PatientRepository;

import java.sql.SQLException;

public class AppointmentService {
    private final AppointmentRepository appointmentRepo;
    private final DoctorAvailabilityService availabilityService;
    private final DoctorRepository doctorRepo;
    private final PatientRepository patientRepo;

    public AppointmentService(AppointmentRepository appointmentRepo,
                              DoctorAvailabilityService availabilityService,
                              DoctorRepository doctorRepo,
                              PatientRepository patientRepo) {
        this.appointmentRepo = appointmentRepo;
        this.availabilityService = availabilityService;
        this.doctorRepo = doctorRepo;
        this.patientRepo = patientRepo;
    }

    public void addDoctor(Doctor doctor) throws SQLException {
        doctorRepo.save(doctor);
    }

    public void addPatient(Patient patient) throws SQLException {
        patientRepo.save(patient);
    }

    public void bookAppointment(Appointment app) throws SQLException, AppointmentException {
        if (!availabilityService.isAvailable(app.getDoctorId(), app.getAppointmentTime())) {
            throw new TimeSlotOccupiedException();
        }
        appointmentRepo.save(app);
    }

    public void cancelAppointment(int appointmentId) throws SQLException, AppointmentException {
        appointmentRepo.updateStatus(appointmentId, "CANCELLED");
    }
}