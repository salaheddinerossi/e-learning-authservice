package com.example.authservice.service;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.StudentDto;
import com.example.authservice.dto.TeacherDto;
import com.example.authservice.model.User;

import java.util.Optional;

public interface UserService {

    void RegisterStudent(StudentDto studentDto);

    void RegisterAdmin(AdminDto adminDto);

    void RegisterTeacher(TeacherDto teacherDto);

    Optional<User> findUserByEmail(String email);

}
