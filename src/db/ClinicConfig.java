package db;

import java.time.LocalTime;

    public class ClinicConfig {
        private static ClinicConfig instance;

        private LocalTime openingTime;
        private LocalTime closingTime;
        private int appointmentDurationMinutes;
        private String clinicName;

        private ClinicConfig() {
            this.openingTime = LocalTime.of(8, 0);
            this.closingTime = LocalTime.of(20, 0);
            this.appointmentDurationMinutes = 30;
            this.clinicName = "Медицинский Центр Здоровья";
        }

        public static synchronized ClinicConfig getInstance() {
            if (instance == null) {
                instance = new ClinicConfig();
            }
            return instance;
        }

        public LocalTime getOpeningTime() {
            return openingTime;
        }

        public LocalTime getClosingTime() {
            return closingTime;
        }

        public int getAppointmentDurationMinutes() {
            return appointmentDurationMinutes;
        }

        public String getClinicName() {
            return clinicName;
        }

        public void setOpeningTime(LocalTime openingTime) {
            this.openingTime = openingTime;
        }

        public void setClosingTime(LocalTime closingTime) {
            this.closingTime = closingTime;
        }

        public void setAppointmentDurationMinutes(int minutes) {
            this.appointmentDurationMinutes = minutes;
        }

        public void setClinicName(String clinicName) {
            this.clinicName = clinicName;
        }

        @Override
        public String toString() {
            return "ClinicConfig{" +
                    "clinicName='" + clinicName + '\'' +
                    ", openingTime=" + openingTime +
                    ", closingTime=" + closingTime +
                    ", appointmentDurationMinutes=" + appointmentDurationMinutes +
                    '}';
        }
    }

