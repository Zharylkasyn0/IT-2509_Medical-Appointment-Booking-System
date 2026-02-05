package db.services;

import db.entities.Doctor;
import db.repositories.IRepository;
import db.utils.Result;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    // Используем интерфейс с дженериком <Doctor>
    private final IRepository<Doctor> doctorRepository;

    public DoctorService(IRepository<Doctor> doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Result<List<Doctor>> getDoctorsBySpecialization(String specialization) {
        // Теперь findAll() существует в интерфейсе
        Result<List<Doctor>> result = doctorRepository.findAll();

        if (result.isSuccess()) {
            // Лямбда-выражение (Требование №3) [cite: 15, 35]
            List<Doctor> filtered = result.getData().stream()
                    .filter(doc -> doc.getSpecialization().equalsIgnoreCase(specialization))
                    .collect(Collectors.toList());
            return new Result<>(filtered);
        } else {
            // Теперь этот конструктор существует в классе Result
            return new Result<>(false, null, result.getErrorMessage());
        }
    }

    public Result<List<Doctor>> getDoctorsSortedByName() {
        Result<List<Doctor>> result = doctorRepository.findAll();

        if (result.isSuccess()) {
            List<Doctor> allDoctors = result.getData();
            // Лямбда для сортировки (Требование №3)
            allDoctors.sort((d1, d2) -> d1.getName().compareToIgnoreCase(d2.getName()));
            return new Result<>(allDoctors);
        } else {
            return new Result<>(false, null, result.getErrorMessage());
        }
    }
}