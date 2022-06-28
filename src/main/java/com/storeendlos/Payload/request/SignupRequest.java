package com.storeendlos.Payload.request;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class SignupRequest {

    private String name;

    private String email;

    private Set<String> roles;

    private String password;

    private String phoneno;

    private String address;

    private String telephoneNumber;

    private LocalDate dob;

    private String maritalStatus;

    private String nativePalace;

    private String nationality;

    private int pinCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getNativePalace() {
        return nativePalace;
    }

    public void setNativePalace(String nativePalace) {
        this.nativePalace = nativePalace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date datetime;

    @PrePersist
    private void Createdate() {
        datetime = new Date();
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getRoles() {
        System.out.println("sign up Request ");
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }




}
