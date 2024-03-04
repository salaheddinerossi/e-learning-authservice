package com.example.authservice.serviceImpl;

import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.StudentDto;
import com.example.authservice.dto.TeacherDto;
import com.example.authservice.exception.EmailAlreadyUsedException;
import com.example.authservice.model.Admin;
import com.example.authservice.model.Student;
import com.example.authservice.model.Teacher;
import com.example.authservice.model.User;
import com.example.authservice.repository.UserRepository;
import com.example.authservice.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    final
    UserRepository userRepository;

    final
    PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void RegisterStudent(StudentDto studentDto) {
        Student student = new Student();

        if(isEmailUsed(studentDto.getEmail())){
            throw new EmailAlreadyUsedException();
        }

        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setEmail(studentDto.getEmail());
        student.setPassword(passwordEncoder.encode(studentDto.getPassword()));

        userRepository.save(student);
    }

    @Override
    @Transactional
    public void RegisterAdmin(AdminDto adminDto) {
        Admin admin = new Admin();

        if(isEmailUsed(adminDto.getEmail())){
            throw new EmailAlreadyUsedException();
        }

        admin.setFirstName(adminDto.getFirstName());
        admin.setLastName(adminDto.getLastName());
        admin.setEmail(adminDto.getEmail());
        admin.setPassword(passwordEncoder.encode(adminDto.getPassword()));

        userRepository.save(admin);
    }

    @Override
    @Transactional
    public void RegisterTeacher(TeacherDto teacherDto) {
        Teacher teacher = new Teacher();

        if(isEmailUsed(teacherDto.getEmail())){
            throw new EmailAlreadyUsedException();
        }

        teacher.setFirstName(teacherDto.getFirstName());
        teacher.setLastName(teacherDto.getLastName());
        teacher.setEmail(teacherDto.getEmail());
        teacher.setPassword(passwordEncoder.encode(teacherDto.getPassword()));
        teacher.setPhoneNumber(teacherDto.getPhoneNumber());

        userRepository.save(teacher);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    Boolean isEmailUsed(String email){
        return findUserByEmail(email).isPresent();
    }

}
