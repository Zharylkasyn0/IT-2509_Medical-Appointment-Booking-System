package db.services;

import db.entities.Appointment;
import db.entities.Doctor;
import db.entities.Patient;
import db.exeption.AppointmentException;
import db.repositories.AppointmentRepository;
import db.repositories.DoctorRepository;
import db.repositories.PatientRepository;
import db.utils.Result;
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

    public Result<Boolean> addDoctor(Doctor doctor) {
        return doctorRepo.save(doctor);
    }

    public Result<Boolean> addPatient(Patient patient) {
        try {
            patientRepo.save(patient);
            return new Result<>(true);
        } catch (SQLException e) {
            return new Result<>(e.getMessage());
        }
    }

    public Result<Boolean> bookAppointment(Appointment app) {
        try {
            if (!availabilityService.isAvailable(app.getDoctorId(), app.getAppointmentTime())) {
                throw new AppointmentException("This time slot is already booked.");
            }
            appointmentRepo.save(app);
            return new Result<>(true);
        } catch (SQLException | AppointmentException e) {
            return new Result<>(e.getMessage());
        }
    }

    public Result<Boolean> cancelAppointment(int appointmentId) {
        try {
            appointmentRepo.updateStatus(appointmentId, "CANCELLED");
            return new Result<>(true);
        } catch (SQLException | AppointmentException e) {
            return new Result<>(e.getMessage());
        }
    }
}