package db.services;


import db.entities.Doctor;
import db.repositories.IRepository;
import java.util.List;
import java.util.stream.Collectors;

public class DoctorService {
    private IRepository<Doctor> doctorRepository;

    public DoctorService(IRepository<Doctor> doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        List<Doctor> allDoctors = doctorRepository.getAll();
        return allDoctors.stream()
                .filter(doc -> doc.getSpecialization().equalsIgnoreCase(specialization))
                .collect(Collectors.toList());
    }

    public List<Doctor> getDoctorsSortedByName() {
        List<Doctor> allDoctors = doctorRepository.getAll();
        allDoctors.sort((d1, d2) -> d1.getName().compareTo(d2.getName()));
        return allDoctors;
    }
}
