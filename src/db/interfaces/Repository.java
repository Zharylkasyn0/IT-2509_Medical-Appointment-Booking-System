package db.interfaces;

import java.util.List;
import java.util.Optional;


public interface Repository<T> {
    void add(T item);
    Optional<T> findById(int id);
    List<T> findAll();
    void delete(int id);
}