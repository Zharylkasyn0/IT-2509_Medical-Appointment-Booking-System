package edu.aitu.oop3.db.repositories;


import java.util.List;

// T - это тип данных (Doctor, Patient, Appointment и т.д.)
public interface IRepository<T> {
    boolean add(T entity);
    T getById(int id);
    List<T> getAll();
    boolean delete(int id);
}