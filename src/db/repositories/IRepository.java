package db.repositories;

import db.entities.Doctor;
import db.utils.Result;

import java.sql.SQLException;
import java.util.List;

// Измените IRepository.java
// Измените IRepository для использования Result<T>
public interface IRepository<T> {
    Result<T> findById(int id);
    Result<List<T>> getAll();
    Result<Boolean> save(T entity);  // возвращает Result<Boolean>
    Result<Boolean> delete(int id);
}

        // Тогда в JdbcDoctorRepository:
        @Override
        public Result<Boolean> save(Doctor doctor) {
            try {
                // ... выполнить запрос
                int rows = stmt.executeUpdate();
                return new Result<>(rows > 0);
            } catch (SQLException e) {
                return new Result<>(e.getMessage());
            }
        }