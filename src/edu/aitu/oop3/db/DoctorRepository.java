package edu.aitu.oop3.db;
import java.sql.SQLException;
import java.util.List;

public interface DoctorRepository {
    Doctor findById(int id) throws SQLException;
    List<Doctor> findAll() throws SQLException;
}
