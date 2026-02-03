package db.services;

public class InPersonAppointment implements IAppointmentType {
    @Override
    public String getDescription() {
        return "Личный визит в клинику";
    }
}
