package db.repositories;


import java.util.List;
import java.util.Optional;
public interface IRepository<T> {
    Optional<T> findById(int id);
    List<T> getAll();
    boolean save(T entity);
    boolean delete(int id);
}