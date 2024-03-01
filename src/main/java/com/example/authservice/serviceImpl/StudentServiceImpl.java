package com.example.authservice.serviceImpl;


import com.example.authservice.dto.StudentDto;
import com.example.authservice.exception.StudentNotFoundException;
import com.example.authservice.model.Student;
import com.example.authservice.repository.StudentRepository;
import com.example.authservice.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    final
    StudentRepository studentRepository;

    final
    PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository, PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(
                StudentNotFoundException::new
        );
    }

    @Override
    public Optional<Student> findStudentByEmail(String email) {
        return studentRepository.findByEmail(email);
    }

    @Override
    public void registerStudent(StudentDto studentDto) {

        Student student = new Student();

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getPassword());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        studentRepository.save(student);

    }
}
