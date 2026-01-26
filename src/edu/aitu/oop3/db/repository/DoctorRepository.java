package edu.aitu.oop3.db.repository;
import edu.aitu.oop3.db.entities.Doctor;

import java.sql.SQLException;
import java.util.List;

public interface DoctorRepository {
    Doctor findById(int id) throws SQLException;
    List<Doctor> findAll() throws SQLException;
}
