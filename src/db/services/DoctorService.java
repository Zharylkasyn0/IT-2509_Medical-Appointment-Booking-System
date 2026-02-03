package db.services;

import db.entities.Doctor;
import db.repositories.IRepository;
import db.utils.Result;

import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    private IRepository<Doctor> doctorRepository;

    public DoctorService(IRepository<Doctor> doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Result<List<Doctor>> getDoctorsBySpecialization(String specialization) {
        Result<List<Doctor>> result = doctorRepository.getAll();
        if (result.isSuccess()) {
            List<Doctor> filtered = result.getData().stream()
                    .filter(doc -> doc.getSpecialization().equalsIgnoreCase(specialization))
                    .collect(Collectors.toList());
            return new Result<>(filtered);
        } else {
            return new Result<>(result.getErrorMessage()); // Убрали лишний >
        }
    }

    public Result<List<Doctor>> getDoctorsSortedByName() {
        Result<List<Doctor>> result = doctorRepository.getAll();
        if (result.isSuccess()) {
            List<Doctor> allDoctors = result.getData();
            allDoctors.sort((d1, d2) -> d1.getName().compareTo(d2.getName()));
            return new Result<>(allDoctors);
        } else {
            return new Result<>(result.getErrorMessage()); // Убрали лишний >
        }
    }
}
