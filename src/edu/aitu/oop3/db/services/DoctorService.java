package edu.aitu.oop3.db.services;


import edu.aitu.oop3.db.entities.Doctor;
import edu.aitu.oop3.db.repositories.IRepository;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    private IRepository<Doctor> doctorRepository;

    public DoctorService(IRepository<Doctor> doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    // Использование Lambda и Stream API для фильтрации
    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> allDoctors = doctorRepository.getAll();

        return allDoctors.stream()
                .filter(doc -> doc.getSpecialization().equalsIgnoreCase(specialization)) // Лямбда
                .collect(Collectors.toList());
    }

    // Использование Lambda для сортировки по имени
    public List<Doctor> getDoctorsSortedByName() {
        List<Doctor> allDoctors = doctorRepository.getAll();

        allDoctors.sort((d1, d2) -> d1.getName().compareTo(d2.getName())); // Лямбда
        return allDoctors;
    }
}
