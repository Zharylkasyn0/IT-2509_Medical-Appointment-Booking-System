package db.repositories;

import db.entities.Doctor;
import db.utils.Result;

import java.sql.SQLException;
import java.util.List;


public interface IRepository<T> {
    Result<T> findById(int id);
    Result<List<T>> findAll();
    Result<Boolean> save(T entity);  // возвращает Result<Boolean>
    Result<Boolean> delete(int id);
}

