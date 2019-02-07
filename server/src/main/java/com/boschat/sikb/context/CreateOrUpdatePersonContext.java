package com.boschat.sikb.context;

import com.boschat.sikb.model.Formation;
import com.boschat.sikb.model.MedicalCertificate;
import com.boschat.sikb.model.PersonForUpdate;
import com.boschat.sikb.model.Sex;

import java.time.LocalDate;
import java.util.List;

public class CreateOrUpdatePersonContext {

    private String firstName;

    private String name;

    private Sex sex = null;

    private LocalDate birthDate;

    private String address;

    private String postalCode;

    private String city;

    private String phoneNumber;

    private String email;

    private String nationality;

    private List<Formation> formations = null;

    private MedicalCertificate medicalCertificate;

    public static CreateOrUpdatePersonContext create(PersonForUpdate person) {
        CreateOrUpdatePersonContext createOrUpdateContext = new CreateOrUpdatePersonContext();
        createOrUpdateContext.setFirstName(person.getFirstName());
        createOrUpdateContext.setName(person.getName());
        createOrUpdateContext.setSex(person.getSex());
        createOrUpdateContext.setBirthDate(person.getBirthDate());
        createOrUpdateContext.setAddress(person.getAddress());
        createOrUpdateContext.setPostalCode(person.getPostalCode());
        createOrUpdateContext.setCity(person.getCity());
        createOrUpdateContext.setPhoneNumber(person.getPhoneNumber());
        createOrUpdateContext.setEmail(person.getEmail());
        createOrUpdateContext.setNationality(person.getNationality());
        createOrUpdateContext.setFormations(person.getFormations());
        return createOrUpdateContext;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public List<Formation> getFormations() {
        return formations;
    }

    public void setFormations(List<Formation> formations) {
        this.formations = formations;
    }

    public MedicalCertificate getMedicalCertificate() {
        return medicalCertificate;
    }

    public void setMedicalCertificate(MedicalCertificate medicalCertificate) {
        this.medicalCertificate = medicalCertificate;
    }
}
