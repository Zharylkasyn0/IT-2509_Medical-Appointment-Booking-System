package db.interfaces;

public interface IUser {
    // Этот метод обязаны реализовать и DoctorUser, и PatientUser
    void showRole();
}