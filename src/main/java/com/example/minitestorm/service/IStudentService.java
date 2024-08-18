package com.example.minitestorm.service;

import com.example.minitestorm.model.Student;

import java.util.List;

public interface IStudentService {
    List<Student> findAll();
    Student findById(int id);
    void save(Student student);
    void delete(int id);
    void update(int id ,Student student);
}
