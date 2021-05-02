/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vaccinationsystem;

/**
 *
 * @author HP
 */
public class Report {
    protected String patientName;
    protected String doctor;
    protected String hospitalname;
    protected String vaccineName;
    
    public Report(String patientName, String doctor, String hospitalName, String vaccineName)
    {
        this.doctor = doctor;
        this.patientName = patientName;
        this.hospitalname = hospitalName;
        this.vaccineName = vaccineName;
    }
    
    public String getPatientName() {
        return patientName;
    }

    public String getDoctor() {
        return doctor;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getHospitalname() {
        return hospitalname;
    }

    public void setHospitalname(String hospitalname) {
        this.hospitalname = hospitalname;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }
}
