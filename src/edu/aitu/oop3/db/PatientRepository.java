// Файл: edu/aitu/oop3/db/PatientRepository.java
package edu.aitu.oop3.db;
import java.sql.SQLException;

public interface PatientRepository {
    Patient findById(int id) throws SQLException;
}
