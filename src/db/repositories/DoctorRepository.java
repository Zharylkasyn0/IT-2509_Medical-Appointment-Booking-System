package db.repositories;

import db.entities.Doctor;
import db.utils.Result;

import java.util.List;


public interface DoctorRepository extends IRepository<Doctor> {
    Result<List<Doctor>> findAll();
}