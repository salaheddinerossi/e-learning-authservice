package com.example.authservice.controller;


import com.example.authservice.dto.AdminDto;
import com.example.authservice.dto.StudentDto;
import com.example.authservice.dto.TeacherDto;
import com.example.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
public class RegisterController {

    final
    UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminDto adminDto){

        userService.RegisterAdmin(adminDto);
        return ResponseEntity.ok("admin created");

    }

    @PostMapping("/student")
    public ResponseEntity<?> registerStudent(@RequestBody StudentDto studentDto){

        userService.RegisterStudent(studentDto);
        return ResponseEntity.ok("student created");

    }

    @PostMapping("/teacher")
    public ResponseEntity<?> registerTeacher(@RequestBody TeacherDto teacherDto){

        userService.RegisterTeacher(teacherDto);
        return ResponseEntity.ok("teacher created");

    }


}
