package com.example.authservice.service;

import com.example.authservice.dto.ChangePasswordDto;
import com.example.authservice.dto.TeacherDto;
import com.example.authservice.model.Teacher;

import java.util.Optional;

public interface TeacherService {

    Teacher getTeacher(Long teacherID);

    Optional<Teacher> findTeacherByEmail(String email);

    void registerTeacher(TeacherDto teacherDto);



}
