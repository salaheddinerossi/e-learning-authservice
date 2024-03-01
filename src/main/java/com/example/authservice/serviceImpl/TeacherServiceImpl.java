package com.example.authservice.serviceImpl;

import com.example.authservice.dto.TeacherDto;
import com.example.authservice.exception.TeacherNotFoundException;
import com.example.authservice.model.Teacher;
import com.example.authservice.repository.TeacherRepository;
import com.example.authservice.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    TeacherRepository teacherRepository;
    @Override
    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).orElseThrow(
                TeacherNotFoundException::new
        );
    }

    @Override
    public Optional<Teacher> findTeacherByEmail(String email) {
        return teacherRepository.findByEmail(email);
    }

    @Override
    public void registerTeacher(TeacherDto teacherDto) {

        Teacher teacher = new Teacher();

        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        teacher.setPhoneNumber(teacherDto.getPhoneNumber());

        teacherRepository.save(teacher);

    }
}
