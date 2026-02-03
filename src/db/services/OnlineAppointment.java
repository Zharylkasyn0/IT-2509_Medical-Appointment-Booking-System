package db.services;

public class OnlineAppointment implements IAppointmentType {
    @Override
    public String getDescription() {
        return "Онлайн консультация (Zoom)";
    }
}
