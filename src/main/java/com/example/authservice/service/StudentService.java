package com.example.authservice.service;

import com.example.authservice.dto.ChangePasswordDto;
import com.example.authservice.dto.StudentDto;
import com.example.authservice.model.Student;

import java.util.Optional;

public interface StudentService {

    Student getStudent(Long id);

    Optional<Student> findStudentByEmail(String email);


    void registerStudent(StudentDto studentDto);

}
