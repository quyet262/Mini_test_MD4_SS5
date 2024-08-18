package com.example.minitestorm.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;

public class StudentForm {

    private int id;

    private String fistName;
    private String lastName;
    private String dob;
    private String address;
    private String mark;
    private MultipartFile image;

    public StudentForm() {
    }

    public StudentForm(int id, String fistName, String lastName, String dob, String address, String mark, MultipartFile image) {
        this.id = id;
        this.fistName = fistName;
        this.lastName = lastName;
        this.dob = dob;
        this.address = address;
        this.mark = mark;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
