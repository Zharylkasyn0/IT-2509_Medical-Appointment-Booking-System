package db.repositories;

import db.entities.Patient;
import java.sql.SQLException;

public interface PatientRepository {
    Patient findById(int id) throws SQLException;
    void save(Patient patient) throws SQLException;
}
