package com.storeendlos.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.storeendlos.AuditingAndResponse.Audit;
import com.storeendlos.indent.Model.Indent;
import com.storeendlos.issue.model.IssueItem;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "userRegistration")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User extends Audit<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long userId;
    private String name;
    private String email;
    private String surName;
    private String address;
    @Column(unique = true)
    private String phoneno;
    @Column(name = "user_tele_number", nullable = true)
    private String telephoneNumber;
    @Column(name = "Date_Of_birth", nullable = true)
    private LocalDate dob;
    @Column(name = "marital_status", nullable = true)
    private String maritalStatus;
    @Column(name = "native_palace", nullable = true)
    private String nativePalace;
    @Column(name = "nationality", nullable = true)
    private String nationality;
    private String pinCode;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date datetime;

    public User(String name, String email, String address, String phoneno, String telephoneNumber, LocalDate dob, String maritalStatus, String nativePalace, String nationality, int pinCode) {

    }

    public User() {

    }

    @PrePersist
    private void Createdate() {
        datetime = new Date();
    }
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "emp",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("emp")
    private Set<IssueItem> issueItemsData;
    @OneToMany(mappedBy = "employee",cascade = CascadeType.ALL)
    @JsonIgnoreProperties("employee")
    private Set<Indent> indentData;

    public Set<Indent> getIndentData() {
        return indentData;
    }

    public void setIndentData(Set<Indent> indentData) {
        this.indentData = indentData;
    }

    public Set<IssueItem> getIssueItemsData() {
        return issueItemsData;
    }

    public void setIssueItemsData(Set<IssueItem> issueItemsData) {
        this.issueItemsData = issueItemsData;
    }

    public Date getDatetime() {
        return datetime;
    }



    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }
}
