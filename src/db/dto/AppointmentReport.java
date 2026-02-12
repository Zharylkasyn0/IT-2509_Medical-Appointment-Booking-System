package db.dto;

import java.util.Map;

public class AppointmentReport {
    private final String clinicName;
    private final Map<String, Long> countByStatus;
    private final Map<String, Long> countByDoctor;

    private AppointmentReport(Builder builder) {
        this.clinicName = builder.clinicName;
        this.countByStatus = builder.countByStatus;
        this.countByDoctor = builder.countByDoctor;
    }


    public String getClinicName() { return clinicName; }
    public Map<String, Long> getCountByStatus() { return countByStatus; }
    public Map<String, Long> getCountByDoctor() { return countByDoctor; }

    @Override
    public String toString() {
        return "AppointmentReport{" +
                "clinicName='" + clinicName + '\'' +
                ", countByStatus=" + countByStatus +
                ", countByDoctor=" + countByDoctor +
                '}';
    }

    public static class Builder {
        private String clinicName;
        private Map<String, Long> countByStatus;
        private Map<String, Long> countByDoctor;

        public Builder setClinicName(String clinicName) {
            this.clinicName = clinicName;
            return this;
        }

        public Builder setCountByStatus(Map<String, Long> countByStatus) {
            this.countByStatus = countByStatus;
            return this;
        }

        public Builder setCountByDoctor(Map<String, Long> countByDoctor) {
            this.countByDoctor = countByDoctor;
            return this;
        }

        public AppointmentReport build() {
            return new AppointmentReport(this);
        }
    }
}