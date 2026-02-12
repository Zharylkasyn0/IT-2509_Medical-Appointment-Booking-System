package db.services;

import db.entities.Appointment;
import db.entities.Doctor;
import db.entities.Patient;
import db.dto.AppointmentReport;
import db.ClinicConfig;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import db.repositories.AppointmentRepository;
import db.repositories.DoctorRepository;
import db.repositories.PatientRepository;
import db.utils.Result;


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
        return patientRepo.save(patient);
    }

    public Result<Boolean> bookAppointment(Appointment app) {
        Result<Boolean> availabilityResult = availabilityService.isAvailable(app.getDoctorId(), app.getAppointmentTime());
        if (!availabilityResult.isSuccess() || !availabilityResult.getData()) {
            return new Result<>("Time slot is not available");
        }
        return appointmentRepo.save(app);
    }

    public Result<Boolean> cancelAppointment(int appointmentId) {
        return appointmentRepo.updateStatus(appointmentId, "CANCELLED");
    }

    public Result<AppointmentReport> generateReport() {
        Result<List<Appointment>> allAppointmentsResult = appointmentRepo.findAll();

        if (!allAppointmentsResult.isSuccess()) {
            return new Result<>(allAppointmentsResult.getErrorMessage());
        }

        List<Appointment> appointments = allAppointmentsResult.getData();
        Map<String, Long> byStatus = appointments.stream()
                .collect(Collectors.groupingBy(
                        Appointment::getStatus,
                        Collectors.counting()
                ));

        Map<String, Long> byDoctor = appointments.stream()
                .collect(Collectors.groupingBy(
                        app -> "Dr. " + app.getDoctorId(),
                        Collectors.counting()
                ));

        String clinicName = ClinicConfig.getInstance().getClinicName();

        AppointmentReport report = new AppointmentReport.Builder()
                .setClinicName(clinicName)
                .setCountByStatus(byStatus)
                .setCountByDoctor(byDoctor)
                .build();

        return new Result<>(report);
    }
}