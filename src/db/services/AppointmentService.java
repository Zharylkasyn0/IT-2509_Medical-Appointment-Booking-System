package db.services;

import db.entities.Appointment;
import db.entities.Doctor;
import db.entities.Patient;
import db.exeption.AppointmentException;
import db.exeption.TimeSlotOccupiedException;
import db.repositories.AppointmentRepository;
import db.repositories.DoctorRepository;
import db.repositories.PatientRepository;

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