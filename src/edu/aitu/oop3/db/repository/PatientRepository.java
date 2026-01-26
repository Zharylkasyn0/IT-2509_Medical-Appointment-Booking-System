// Файл: edu/aitu/oop3/db/PatientRepository.java
package edu.aitu.oop3.db.repository;
import edu.aitu.oop3.db.entities.Patient;

import java.sql.SQLException;

public interface PatientRepository {
    Patient findById(int id) throws SQLException;
}
