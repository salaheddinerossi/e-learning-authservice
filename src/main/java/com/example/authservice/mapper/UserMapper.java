package com.example.authservice.mapper;


import com.example.authservice.dto.UserDto;
import com.example.authservice.model.Admin;
import com.example.authservice.model.Student;
import com.example.authservice.model.Teacher;
import com.example.authservice.response.UserCreatedResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserCreatedResponse fromTeacherToUserCreatedResponse(Teacher teacher);

    UserCreatedResponse fromAdminToUserCreatedResponse(Admin admin);

    UserCreatedResponse fromStudentToUserCreatedResponse(Student student);

    @Mapping(target = "password", ignore = true)
    Student userDtoToStudent(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    Teacher userDtoToTeacher(UserDto userDto);


    @Mapping(target = "password", ignore = true)
    Admin userDtoToAdmin(UserDto userDto);




}
