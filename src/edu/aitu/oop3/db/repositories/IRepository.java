package edu.aitu.oop3.db.repositories;


import java.util.List;
public interface IRepository<T> {
    T findById(int id);
    List<T> getAll();
    boolean add(T entity);
    boolean delete(int id);
}