package com.example.authservice.serviceImpl;

import com.example.authservice.model.Admin;
import com.example.authservice.model.Student;
import com.example.authservice.model.Teacher;
import com.example.authservice.service.AdminService;
import com.example.authservice.service.CustomUserDetailsService;
import com.example.authservice.service.StudentService;
import com.example.authservice.service.TeacherService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {


    final
    AdminService adminService;

    final
    StudentService studentService;

    final
    TeacherService teacherService;

    public CustomUserDetailsServiceImpl(AdminService adminService, StudentService studentService, TeacherService teacherService) {
        this.adminService = adminService;
        this.studentService = studentService;
        this.teacherService = teacherService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {


        Optional<Admin> admin = adminService.findAdminByEmail(username);

        if(admin.isPresent()){
            return new User(
                    admin.get().getEmail(),
                    admin.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        Optional<Student> student = studentService.findStudentByEmail(username);

        if (student.isPresent()){
            return new User(
                    student.get().getEmail(),
                    student.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_STUDENT"))
            );
        }

        Optional<Teacher> teacher = teacherService.findTeacherByEmail(username);

        if (teacher.isPresent()){
            return new User(
                    teacher.get().getEmail(),
                    teacher.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_TEACHER"))
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
