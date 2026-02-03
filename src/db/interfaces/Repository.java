package db.interfaces;
import java.util.List;
import java.util.Optional;

// Обобщенный интерфейс репозитория (Generics)
public interface Repository<T> {
    void add(T item);
    List<T> getAll();
    Optional<T> findById(int id);
}