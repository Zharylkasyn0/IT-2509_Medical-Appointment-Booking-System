package edu.aitu.oop3.db.repositories;

import edu.aitu.oop3.db.repositories.IRepository;
import edu.aitu.oop3.db.entities.Doctor;
import java.sql.SQLException;
import java.util.List;

public interface DoctorRepository extends IRepository<Doctor> {
    Doctor findById(int id) throws SQLException;
    List<Doctor> findAll() throws SQLException;
    void save(Doctor doctor) throws SQLException;
}