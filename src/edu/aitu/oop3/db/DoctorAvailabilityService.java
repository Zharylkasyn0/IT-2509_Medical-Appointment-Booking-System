package edu.aitu.oop3.db;
public class DoctorAvailabilityService {
    private final AppointmentRepository repo;

    public DoctorAvailabilityService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public boolean isSlotAvailable(int doctorId, LocalDateTime time) throws SQLException {
        List<Appointment> apps = repo.findByDoctorId(doctorId);
        return apps.stream().noneMatch(a -> a.getSlotTime().equals(time) && !"CANCELLED".equals(a.getStatus()));
    }
}