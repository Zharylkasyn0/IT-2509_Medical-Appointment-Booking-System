package db.services;

import db.entities.Appointment;
import db.repositories.AppointmentRepository;
import db.utils.Result;

import java.time.LocalDateTime;
import java.util.List;

public class DoctorAvailabilityService {
    private final AppointmentRepository repository;

    public DoctorAvailabilityService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public Result<Boolean> isAvailable(int doctorId, LocalDateTime time) {
        Result<List<Appointment>> appointmentsResult = repository.findByDoctorId(doctorId);
        if (!appointmentsResult.isSuccess()) {
            return new Result<>(appointmentsResult.getErrorMessage());
        }
        List<Appointment> appointments = appointmentsResult.getData();
        for (Appointment app : appointments) {
            if (app.getAppointmentTime().equals(time) &&
                    !"CANCELLED".equalsIgnoreCase(app.getStatus())) {
                return new Result<>(false);  // false - занято
            }
        }
        return new Result<>(true);  // свободно
    }
}